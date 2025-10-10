/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.skinconsultationcentre;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.Dimension;

// Encryption helpers
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {
    
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "MySecretKey12345"; // Must be 16 characters for AES 
    
    private String encrypt(String input) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            return input; // fallback
        }
    }
    
    private String decrypt(String encryptedInput) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedInput));
            return new String(decrypted);
        } catch (Exception e) {
            return encryptedInput; // fallback
        }
    }

    private final ArrayList<Consultation> consultationList = new ArrayList<>();
    private final ArrayList<Doctor> doctorList;
    private int numDoctor; // Maximum number of doctors allowed in the system

    // GUI components 
    JTable myTable;
    JFrame myFrame;
    DoctorTableModel tableModel;

    public WestminsterSkinConsultationManager(int listLength) {
        this.numDoctor = listLength;
        doctorList = new ArrayList<>();
        loadDoctors();
        loadConsultations();
    }

    // Constructor for GUI
    public WestminsterSkinConsultationManager(ArrayList<Doctor> list) {
        doctorList = list; 
        tableModel = new DoctorTableModel(list);
        myTable = new JTable(tableModel);
        myFrame = new JFrame();

        myTable.setAutoCreateRowSorter(true); // enable sorting

        JScrollPane scrollPane = new JScrollPane(myTable);
        scrollPane.setPreferredSize(new Dimension(380, 280));

        JPanel panel = new JPanel();
        panel.add(scrollPane);
        myFrame.add(panel);
        myFrame.pack();
        myFrame.setVisible(true);
    }
    
    //Add a doctor if there's available space
    @Override
    public boolean addDoctor(Doctor doctor) {
        if (doctorList.size() < numDoctor) {
            doctorList.add(doctor);
            return true; // successfully added
        } else {
            System.out.println("No more space in the list (max " + numDoctor + " doctors).");
            return false; // not added
        }
    }

    // Delete a doctor from the system
    @Override
    public void deleteDoctor(Doctor doctor) {
        if (doctor == null) {
            System.out.println("Doctor not found");
        } else {
            doctorList.remove(doctor);
            System.out.println(doctor.getName() + " " + doctor.getSurname() + " has been deleted. Total number of doctors now: " + doctorList.size());
        }
    }

    //Print doctors sorted by surname
    @Override
    public void printDoctorList() {
        Collections.sort(doctorList);
        for (Doctor d : doctorList) {
            System.out.println(d);
        }
    }

    // Add a new doctor to the system 
    @Override
    public void inputDoctor(String name, String surname, LocalDate dob, String mobileNum, int medLicenceNum, String jobSpecialism) {
        
        // validate name/surname
        if (!isValidName(name) || !isValidName(surname)) {
            System.out.println("Invalid name or surname.");
            return;
        }
        if (!isValidMobile(mobileNum)) {
            System.out.println("Invalid mobile number.");
            return;
        }
        if (!isValidLicenceNumber(medLicenceNum)) {
            System.out.println("Invalid medical licence number.");
            return;
        }
        
        // Create a new doctor object
        Doctor doctor = new Doctor(name, surname, dob, mobileNum, medLicenceNum, jobSpecialism);
        if (addDoctor(doctor)) {
            saveDoctors();
            System.out.println("Doctor added and saved.");
        }
    }
    
    // File persistence
    // Save all doctors to a file
    private void saveDoctors() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Doctor.txt"))) { 
            for (Doctor d : doctorList) {
                bw.write("Doctor Name: " + d.getName() + "\n");
                bw.write("Doctor Surname: " + d.getSurname() + "\n");
                bw.write("DOB: " + d.getDob().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
                bw.write("Mobile Number: " + d.getMobileNum() + "\n");
                bw.write("Medical Licence Number: " + d.getMedLicenceNum() + "\n");
                bw.write("Specialisation: " + d.getJobSpecialism() + "\n");
                bw.write("------------------------\n");
                System.out.println("Doctor information saved to file.");
            }
        } catch (IOException e) {
            System.out.println("Error saving doctors: " + e.getMessage());
        }
    }
        
    // Load doctors from file into DoctorList
    private void loadDoctors() {
        doctorList.clear();
        File f = new File("Doctor.txt");
        if (!f.exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            String name = null, surname = null, specialism = null, mobile = null;
            LocalDate dob = null;
            int licence = 0;

            // Read file line by line and extract doctor details
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Doctor Name: ")) {
                    name = line.substring(13).trim();
                } else if (line.startsWith("Doctor Surname: ")) {
                    surname = line.substring(16).trim();
                } else if (line.startsWith("DOB: ")) {
                    dob = LocalDate.parse(line.substring(5).trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } else if (line.startsWith("Mobile Number: ")) {
                    mobile = line.substring(15).trim();
                } else if (line.startsWith("Medical Licence Number: ")) {
                    licence = Integer.parseInt(line.substring(25).trim());
                } else if (line.startsWith("Specialisation: ")) {
                    specialism = line.substring(15).trim();
                } else if (line.startsWith("------------------------")) {
                    // Once full doctor record is read, create Doctor object
                    if (name != null && surname != null && dob != null && mobile != null && specialism != null) {
                        doctorList.add(new Doctor(name, surname, dob, mobile, licence, specialism));
                    }
                    // Reset variables for next doctor
                    name = surname = specialism = mobile = null;
                    dob = null;
                    licence = 0;
                }
            }
            System.out.println("Doctors successfully loaded from file.");
        } catch (Exception e) {
            System.out.println("Error loading doctors: " + e.getMessage());
        } 
    }
    
    // Save Consultations
    private void saveConsultations() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Consultations.txt"))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            for (Consultation c : consultationList) {
                bw.write("Doctor Licence Number: " + c.getDoctor().getMedLicenceNum() + "\n");
                bw.write("Patient Name: " + c.getPatient().getName() + "\n");
                bw.write("Patient Surname: " + c.getPatient().getSurname() + "\n");
                bw.write("Patient DOB: " + c.getPatient().getDob().format(formatter) + "\n");
                bw.write("Patient Mobile: " + c.getPatient().getMobileNum() + "\n");
                bw.write("Patient ID: " + c.getPatient().getPatientId() + "\n");
                bw.write("Date: " + c.getDate().format(formatter) + "\n");
                bw.write("Time: " + c.getTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "\n");
                bw.write("Cost: " + c.getCost() + "\n");
                bw.write("Notes: " + encrypt(c.getNotes()) + "\n");
                bw.write("------------------------\n");
             }
            System.out.println("Consultations successfully saved to file");
        } catch(IOException e){
            System.out.println("Error saving consultations: " + e.getMessage());
        }
    }
    
    // Load Consultations
    private void loadConsultations() {
        consultationList.clear();
        File f = new File("Consultations.txt");
        if (!f.exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            
            int doctorLicence = 0, patientId = 0;
            String patientName = null, patientSurname = null, patientMobile = null, notes = null;
            LocalDate patientDob = null, consultationDate = null;
            java.time.LocalTime consultationTime = null;
            double cost = 0;
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Doctor Licence Number: ")) {
                    doctorLicence = Integer.parseInt(line.substring(24).trim());
                } else if (line.startsWith("Patient ID: ")) {
                    patientId = Integer.parseInt(line.substring(12).trim());
                } else if (line.startsWith("Patient Name: ")) {
                    patientName = line.substring(14).trim();
                } else if (line.startsWith("Patient Surname: ")) {
                    patientSurname = line.substring(17).trim();
                } else if (line.startsWith("Patient DOB: ")) {
                    patientDob = LocalDate.parse(line.substring(13).trim(), formatter);
                } else if (line.startsWith("Patient Mobile: ")) {
                    patientMobile = line.substring(16).trim();
                } else if (line.startsWith("Date: ")) {
                    consultationDate = LocalDate.parse(line.substring(6).trim(), formatter);
                } else if (line.startsWith("Time: ")) {
                    consultationTime = java.time.LocalTime.parse(line.substring(6).trim(), DateTimeFormatter.ofPattern("HH:mm"));
                } else if (line.startsWith("Cost: ")) {
                    cost = Double.parseDouble(line.substring(6).trim());
                } else if (line.startsWith("Notes: ")) {
                    notes = decrypt(line.substring(7).trim());
                } else if (line.startsWith("------------------------")) {
                    // Find doctor by licence number
                    Doctor foundDoctor = null;
                    for (Doctor d : doctorList) {
                        if (d.getMedLicenceNum() == doctorLicence) {
                            foundDoctor = d;
                            break;
                        }
                    }
                    if (foundDoctor != null && patientName != null && patientSurname != null && patientDob != null) {
                        Patient p  = new Patient(patientName, patientSurname, patientDob, patientMobile, patientId);
                        Consultation c = new Consultation(consultationDate, consultationTime, cost, notes, foundDoctor, p);
                        consultationList.add(c);
                    }
                    
                    // Reset variables for next consultation
                    doctorLicence = 0;
                    patientName = patientSurname = patientMobile = notes = null;
                    patientDob = consultationDate = null;
                    consultationTime = null;
                    cost = 0;
                }  
            }
            System.out.println("Consultations successfully loaded from file");
        } catch (Exception e){
            System.out.println("Error loading consultations: " + e.getMessage());
        }
    }
    
    // Open the GUI
    @Override
    public void openGUI() {
        SwingUtilities.invokeLater(() -> new MainDashboard(doctorList, consultationList));
    }

    // Console menu
    @Override
    public boolean runMenu() {
        Scanner s = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            // Display options
            System.out.println("\nMenu:");
            System.out.println("1. Add new Doctor");
            System.out.println("2. Delete Doctor");
            System.out.println("3. Print Doctor List");
            System.out.println("4. Save and Load Data");
            System.out.println("5. Open GUI");
            System.out.println("6. Exit");

            System.out.print("Choose an option (1-6): ");
            
            // check if input is a number from one of the options
            if (!s.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 1 and 6.");
                s.nextLine(); // clear the invalid input
                continue; // restart loop
            }
            
            int choice = s.nextInt();
            s.nextLine();

            switch (choice) {
                case 1:
                    // Add new doctor info
                    System.out.print("First Name: ");
                    String name = s.nextLine();

                    System.out.print("Surname: ");
                    String surname = s.nextLine();

                    System.out.print("Date of Birth (dd/MM/yyyy): ");
                    String dobInput = s.nextLine();
                    LocalDate dob = null;
                    try {
                        dob = LocalDate.parse(dobInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please use dd/MM/yyyy.");
                        continue;
                    }

                    System.out.print("Mobile Number: ");
                    String mobileNum = s.nextLine();
                    if (!isValidMobile(mobileNum)) {
                        System.out.println("Invalid mobile number. Use 0XXXXXXXXXX or +XXXXXXXXXXX (digits only).");
                        break;
                    }

                    System.out.print("Medical Licence Number: ");
                    int medLicenceNum = s.nextInt();
                    s.nextLine();

                    System.out.print("Specialism: ");
                    String specialism = s.nextLine();

                    inputDoctor(name, surname, dob, mobileNum, medLicenceNum, specialism);
                    break;

                case 2:
                    // Delete doctor by licence number
                    System.out.print("Enter medical licence number to delete: ");
                    int licenseToDelete = s.nextInt();
                    Doctor found = null;
                    for (Doctor d : doctorList) {
                        if (d.getMedLicenceNum() == licenseToDelete) {
                            found = d;
                            break;
                        }
                    }
                    if (found != null) {
                        deleteDoctor(found);
                        saveDoctors();
                    } else {
                        System.out.println("Doctor not found.");
                    }
                    break;

                case 3:
                    // Print sorted doctor list
                    printDoctorList();
                    break;

                case 4:
                    // Save then reload doctors and consultations
                    saveDoctors();
                    saveConsultations();
                    loadDoctors();
                    loadConsultations();
                    break;

                case 5:
                    // Open GUI
                    openGUI();
                    break;

                case 6:
                    // Exit program
                    System.out.println("Exiting and saving data...");
                    exit = true;
                    saveDoctors();
                    saveConsultations();
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        return exit;
    }

    // Validate name format. 
    private boolean isValidName(String str) {
        return str != null && str.matches("^[A-Za-z'\\- ]+$");
    }
    
    private boolean isValidLicenceNumber(int licence) {
        return licence > 0;
    }
    
    // Validate mobile number format. 
    //Accepts either 0XXXXXXXXXX (local) or +XXXXXXXXXXX (international).
    private boolean isValidMobile(String mobile) {
        String digits = mobile.replaceAll("\\s+", ""); // Remove spaces
        return digits.matches("^0\\d{9,10}$") || digits.matches("^\\+\\d{10,15}$");
    }
}
