/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.skinconsultationcentre;

import java.time.LocalDate;

/**
*
* @author Amanah
*/

public class Patient extends Person {
    
    private static int idCounter = 1; // shared across all patients
    private int patientId;

    // Auto-generate ID (for new patients)
    public Patient(String name, String surname, LocalDate dob, String mobileNum){
        super(name, surname, dob, mobileNum);
        this.patientId = idCounter++;
    }
    
    // Load with existing ID (for file loading)
    public Patient(String name, String surname, LocalDate dob, String mobileNum, int patientId) {
        super(name, surname, dob, mobileNum);
        this.patientId = patientId;
    }
    
    public int getPatientId() {
        return patientId;
    }
    
    @Override
    public String toString(){
        return super.toString() + ", patient id: " + patientId;
    }
}
