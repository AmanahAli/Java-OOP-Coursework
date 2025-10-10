/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.skinconsultationcentre;

import java.time.LocalDate;
import java.time.LocalTime;
import java.io.Serializable;

/**
 *
 * @author Amanah
 */
public class Consultation implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private LocalDate date;
    private LocalTime time;
    private double cost;
    private String notes; 
    private Doctor doctor;
    private Patient patient;
    
    public Consultation(LocalDate date, LocalTime time, double cost, String notes, Doctor doctor, Patient patient){
        this.date = date;
        this.time = time;
        this.cost = cost;
        this.notes = notes;
        this.doctor = doctor;
        this.patient = patient;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setTime(LocalTime time) {
        this.time = time;
    }
    
    public LocalTime getTime() {
        return time;
    }
    
    public void setCost(double cost) {
        this.cost = cost; 
    }
    
    public double getCost() {
        return cost;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    public Doctor getDoctor() {
        return doctor;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public String toString() {
        return "Consultation [ date = " + date
                + ", time = " + time
                + ", cost = " + cost
                + ", doctor = " + doctor.getSurname() 
                + ", patient = " + patient.getSurname() + "]";
    }
}
        