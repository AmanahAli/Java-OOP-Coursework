/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.skinconsultationcentre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 *
 * @author Amanah
 */

public class MainDashboard extends JFrame {
    private ArrayList<Doctor> doctorList;
    private ArrayList<Consultation> consultationList;
    
    public MainDashboard(ArrayList<Doctor> doctorList, ArrayList<Consultation> consultationList) {
        this.doctorList = doctorList;
        this.consultationList = consultationList;
        
        //Frame settings
        setTitle("Skin Consultation Centre");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Frame background
        getContentPane().setBackground(new Color(240, 248, 255)); // AliceBlue

        // Main panel with grid layout 
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 248, 255)); 
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Title label
        JLabel titleLabel = new JLabel("Skin Consultation Centre", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(25, 25, 112)); // Midnight Blue
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(30));
        
        // Buttons
        JButton viewDoctorsBtn = createStyledButton("View Doctors", new Color(70, 130, 180));

        JButton bookConsultationBtn = createStyledButton("Book Consultation", new Color(60, 179, 113));

        JButton viewConsultationsBtn = createStyledButton("View Consultations", new Color(255, 140, 0));
       
        // Open doctor list panel
        viewDoctorsBtn.addActionListener((ActionEvent e) -> {
            new DoctorListPanel(doctorList);
        });
        
        // Open consultation booking panel
        bookConsultationBtn.addActionListener((ActionEvent e) -> {
            new ConsultationBookingPanel(doctorList, consultationList);
        });
        
        // Open consultation viewing panel
        viewConsultationsBtn.addActionListener((ActionEvent e) -> {
            new ConsultationViewPanel(consultationList);
        });
        
        // Add buttons to panel
        panel.add(viewDoctorsBtn);
         panel.add(Box.createVerticalStrut(20));
        panel.add(bookConsultationBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(viewConsultationsBtn);
        
        // Add panel to frame
        add(panel);
        setVisible(true);
    }
    
    // Helper to style buttons
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(220, 45));
        button.setMaximumSize(new Dimension(220, 45));
        
        // Rounded border
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 2, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
}
