/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.skinconsultationcentre;

import java.time.LocalDate;

/**
 *
 * @author Amanah
 */
public interface SkinConsultationManager {
    public abstract boolean addDoctor(Doctor doctor);
    public abstract void deleteDoctor(Doctor doctor);
    public abstract void printDoctorList();
    public abstract void inputDoctor(String name, String surname, LocalDate dob, String mobileNum, int medLicenceNum, String jobSpecialism);
    public abstract void openGUI();
    public abstract boolean runMenu();
    
}


