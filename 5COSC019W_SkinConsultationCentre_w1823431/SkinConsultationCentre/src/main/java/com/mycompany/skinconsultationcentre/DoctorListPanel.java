/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.skinconsultationcentre;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.table.JTableHeader;


/**
 *
 * @author Amanah
 */
public class DoctorListPanel extends JFrame {
    
    public DoctorListPanel(ArrayList<Doctor> doctorList) {
        setTitle("Doctor List");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Main panel with background color
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(70, 130, 180));

        // Sort doctors alphabetically by surname 
        Collections.sort(doctorList);  
        
        // Create table model with doctor data
        DoctorTableModel model = new DoctorTableModel(doctorList);
        JTable table = new JTable(model);
        
        // Allow table sorting (by clicking headers)
        table.setAutoCreateRowSorter(true);
        
        // Table styling
        table.setBackground(new Color(230, 240, 250));  // Light blue rows
        table.setGridColor(new Color(200, 220, 240));
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Table header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));

        // Add table inside scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}

