/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.skinconsultationcentre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Base64;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Amanah
 */
public class ConsultationViewPanel extends JFrame {
    private JTable consultationTable; // Table to display consultations
    private DefaultTableModel tableModel; // Table model (manages table rows & columns)
    private JButton viewButton; // Button to view details of selected consultation
    private ArrayList<Consultation> consultations; // List of consultations
    
    public ConsultationViewPanel(ArrayList<Consultation> consultations) {
        this.consultations = consultations;
        
        setLayout(new BorderLayout());
       
        // window setup
        setTitle("View Consultations");
        setSize(800, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Table column headers
        String[] columnNames = {"Doctor", "Patient", "Date", "Time", "Cost"};
        tableModel = new DefaultTableModel(columnNames, 0);
        
        // Add each consultation as a row in the table
        for (Consultation c : consultations) {
            String doctorName = c.getDoctor() !=  null ? c.getDoctor().getName() + " " + c.getDoctor().getSurname() : "N/A";
            String patientName = c.getPatient() != null ? c.getPatient().getName() + " " + c.getPatient().getSurname() : "N/A";
            String date = c.getDate().toString(); // Convert LocalDate to String
            String formattedTime = c.getTime().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
            double cost = c.getCost(); // Consultation cost
            
            // Add row to table model
            tableModel.addRow(new Object[]{doctorName, patientName, date, formattedTime, "£" + cost});
        }
        
        // Create table and add it inside a scroll pane
        consultationTable = new JTable(tableModel);
        
        // Table styling
        consultationTable.setFillsViewportHeight(true);
        consultationTable.setSelectionBackground(new Color(184, 207, 229));
        consultationTable.setRowHeight(25);
        consultationTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        // Header styling
        JTableHeader header = consultationTable.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        JScrollPane scrollPane = new JScrollPane(consultationTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // Add the "View Details" button
        viewButton = new JButton("View Details");
        viewButton.setBackground(new Color(100, 149, 237));
        viewButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        viewButton.setFocusPainted(false);
        viewButton.setBorderPainted(false);
        viewButton.setPreferredSize(new Dimension(150, 40));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(230, 240, 255)); // match background
        buttonPanel.add(viewButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Button action
        viewButton.addActionListener(e -> viewDetails());
        
        // Make the window visible
        setVisible(true);
    }
    
    // View full details of the selected consultation (Opens a dialog box with all consultation info)
    private void viewDetails() {
        int selectedRow = consultationTable.getSelectedRow();
        // If no row is selected, show a warning message
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a consultation.");
            return;
        }
        
        // Get the correct row index in case of sorting
        int modelRow = consultationTable.convertRowIndexToModel(selectedRow);
        Consultation c = consultations.get(modelRow); // Retrieve consultation object
        
        String formattedTime = c.getTime().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
        
        // Build consultation details string
        StringBuilder details = new StringBuilder();
        details.append("Doctor: ").append(c.getDoctor().getName()).append(" ").append(c.getDoctor().getSurname()).append("\n");
        details.append("Patient: ").append(c.getPatient().getName()).append(" ").append(c.getPatient().getSurname()).append("\n");
        details.append("Patient ID: ").append(c.getPatient().getPatientId()).append("\n");
        details.append("DOB: ").append(c.getPatient().getDob()).append("\n");
        details.append("Mobile: ").append(c.getPatient().getMobileNum()).append("\n");
        details.append("Date: ").append(c.getDate().toString()).append("\n");
        details.append("Time: ").append(formattedTime).append("\n");
        details.append("Cost: £").append(c.getCost()).append("\n");
        
        // Decrypt Notes
        String decryptedNotes = c.getNotes(); // change this to c.getDecryptedNotes
        details.append("Notes: ").append(decryptedNotes);
        
        // Show details in dialog box
        JOptionPane.showMessageDialog(this, details.toString(), "Consultation Details", JOptionPane.INFORMATION_MESSAGE);
    }
}
