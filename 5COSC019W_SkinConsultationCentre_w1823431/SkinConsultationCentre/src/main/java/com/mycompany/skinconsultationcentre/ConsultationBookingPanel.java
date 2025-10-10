/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.skinconsultationcentre;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class ConsultationBookingPanel extends JFrame {

    public ConsultationBookingPanel(ArrayList<Doctor> doctorList, ArrayList<Consultation> consultationList) {

        setTitle("Book a Consultation");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel 
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(230, 240, 255));

        // Form panel with GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(230, 240, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        Font labelFont = new Font("SansSerif", Font.BOLD, 13);
        
        // Doctor dropdown
        JLabel doctorLabel = new JLabel("Select Doctor:");
        doctorLabel.setFont(labelFont);
        JComboBox<Doctor> doctorDropdown = new JComboBox<>(doctorList.toArray(new Doctor[0]));
        doctorDropdown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Doctor d) {
                    setText(d.getName() + " " + d.getSurname() + " (" + d.getJobSpecialism() + ")");
                }
                return this;
            }
        });

        gbc.gridx = 0; gbc.gridy = row; formPanel.add(doctorLabel, gbc);
        gbc.gridx = 1; formPanel.add(doctorDropdown, gbc);
        row++;

        // Consultation Date picker
        JLabel dateLabel = new JLabel("Consultation Date:");
        dateLabel.setFont(labelFont);
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy"));
        
        gbc.gridx = 0; gbc.gridy = row; formPanel.add(dateLabel, gbc);
        gbc.gridx = 1; formPanel.add(dateSpinner, gbc);
        row++;

        // Time
        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setFont(labelFont);
        String[] times = {"09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00"};
        JComboBox<String> timeDropdown = new JComboBox<>(times);

        gbc.gridx = 0; gbc.gridy = row; formPanel.add(timeLabel, gbc);
        gbc.gridx = 1; formPanel.add(timeDropdown, gbc);
        row++;

        // Patient fields
        String[] fieldLabels = {"Patient First Name:", "Patient Surname:", "Patient DOB:", "Patient Mobile:", "Patient ID:"};
        JTextField nameField = new JTextField(15);
        JTextField surnameField = new JTextField(15);
        JSpinner dobSpinner = new JSpinner(new SpinnerDateModel());
        dobSpinner.setEditor(new JSpinner.DateEditor(dobSpinner, "dd/MM/yyyy"));
        JTextField mobileField = new JTextField(15);
        JTextField patientIdField = new JTextField(15);
        
        Component[] inputs = {nameField, surnameField, dobSpinner, mobileField, patientIdField};
        
        for (int i = 0; i < fieldLabels.length; i++) {
            JLabel lbl = new JLabel(fieldLabels[i]);
            lbl.setFont(labelFont);
            gbc.gridx = 0; gbc.gridy = row; formPanel.add(lbl, gbc);
            gbc.gridx = 1; formPanel.add(inputs[i], gbc);
            row++;
        }

        // Notes
        JLabel notesLabel = new JLabel("Notes:");
        notesLabel.setFont(labelFont);
        JTextArea notesArea = new JTextArea(5, 20);
        JScrollPane notesScroll = new JScrollPane(notesArea);

        gbc.gridx = 0; gbc.gridy = row; formPanel.add(notesLabel, gbc);
        gbc.gridx = 1; formPanel.add(notesScroll, gbc);
        row++;

        mainPanel.add(formPanel);

        // Button panel 
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(230, 240, 255));
        
        JButton bookBtn = new JButton("Book Consultation");
        bookBtn.setBackground(new Color(0, 120, 215));
        bookBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        bookBtn.setFocusPainted(false);
        bookBtn.setPreferredSize(new Dimension(200, 40));
        
        buttonPanel.add(bookBtn);
        mainPanel.add(buttonPanel);

        add(mainPanel);

        // Button action listener
        bookBtn.addActionListener(e -> {
            try {
                // Patient DOB
                java.util.Date dobDate = (java.util.Date) dobSpinner.getValue();
                LocalDate dob = dobDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

                // Consultation Date
                java.util.Date utilDate = (java.util.Date) dateSpinner.getValue();
                LocalDate date = utilDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

                // Time
                String selectedTime = (String) timeDropdown.getSelectedItem();
                int hour = Integer.parseInt(selectedTime.split(":")[0]);

                int id = Integer.parseInt(patientIdField.getText().trim());
                Doctor selectedDoctor = (Doctor) doctorDropdown.getSelectedItem();

                // Check doctor availability
                boolean isAvailable = consultationList.stream().noneMatch(c ->
                        c.getDate().equals(date) &&
                        c.getTime().equals(LocalTime.of(hour, 0)) &&
                        selectedDoctor.equals(c.getDoctor())
                );

                Doctor finalDoctor = selectedDoctor;

                if (!isAvailable) {
                    // Find alternative available doctors
                    ArrayList<Doctor> availableDoctors = new ArrayList<>();
                    for (Doctor d : doctorList) {
                        boolean available = consultationList.stream().noneMatch(c ->
                                c.getDate().equals(date) &&
                                c.getTime().equals(LocalTime.of(hour, 0)) &&
                                c.getDoctor().equals(d));
                        if (available) availableDoctors.add(d);
                    }

                    if (!availableDoctors.isEmpty()) {
                        finalDoctor = availableDoctors.get(new Random().nextInt(availableDoctors.size()));
                        JOptionPane.showMessageDialog(this,
                                "Selected doctor is not available. Booked with: " + finalDoctor.getName());
                    } else {
                        JOptionPane.showMessageDialog(this, "No doctors are available at this time.");
                        return;
                    }
                }

                // Consultation cost 
                double costPerHour = 15; // Default for first consultation
                for (Consultation c : consultationList) {
                    if (c.getPatient().getPatientId() == id) {
                        costPerHour = 25;
                        break;
                    }
                }
                int duration = 1;
                double cost = costPerHour * duration;

                // Create Patient
                Patient patient = new Patient(
                        nameField.getText().trim(),
                        surnameField.getText().trim(),
                        dob,
                        mobileField.getText().trim(),
                        id
                );

                // Create Consultation 
                LocalTime time = LocalTime.of(hour, 0);
                Consultation consultation = new Consultation(date, time, cost, notesArea.getText(), finalDoctor, patient);
                // Add consultation to list
                consultationList.add(consultation);

                // Show success message with all details
                JOptionPane.showMessageDialog(this,
                        "Consultation successfully booked with Dr. " + finalDoctor.getSurname() + "\n" +
                        "Patient: " + patient.getName() + " " + patient.getSurname() + "\n" +
                        "Date: " + date + " at " + hour + ":00\n" +
                        "Cost: £" + cost
                );

                this.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        setVisible(true);

    }
}
