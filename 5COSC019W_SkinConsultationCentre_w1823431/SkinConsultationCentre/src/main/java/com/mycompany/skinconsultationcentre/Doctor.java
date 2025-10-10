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
public class Doctor extends Person implements Comparable<Doctor> {
    private int medLicenceNum;
    private String jobSpecialism;
    
    public Doctor(String name, String surname, LocalDate dob, String mobileNum, int medLicenceNum, String jobSpecialism){
        super(name, surname, dob, mobileNum);
        this.medLicenceNum = medLicenceNum;
        this.jobSpecialism = jobSpecialism;
    }

    public void setMedLicenceNum(int medLicenceNum) {
        this.medLicenceNum = medLicenceNum;
    }
    
    public int getMedLicenceNum() {
        return medLicenceNum;
    }
    
    public void setJobSpecialism(String jobSpecialism) {
        this.jobSpecialism = jobSpecialism;
    }
    
    public String getJobSpecialism() {
        return jobSpecialism;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", License: " + medLicenceNum + ", Specialism: " + jobSpecialism;
    }
    
    @Override
    public int compareTo(Doctor other) {
        return this.getSurname().compareToIgnoreCase(other.getSurname());
    }
    }
        
