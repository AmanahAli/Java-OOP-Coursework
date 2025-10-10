/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.skinconsultationcentre;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;

/**
 *
 * @author Amanah
 */
public class Date {
    private LocalDate date;
    
    public Date(int day, int month, int year) {
        try {
            this.date = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            System.out.println("Invalid date: " + e.getMessage());
        }
    }
    
    public void setDate(int day, int month, int year) {
        try {
            this.date = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            System.out.println("Invalid date: " + e.getMessage());
        }
    }
    
    public int getYear() {
        return date != null ? date.getYear() : -1;
    }

    public int getMonth() {
        return date != null ? date.getMonthValue() : -1;
    }

    public int getDay() {
        return date != null ? date.getDayOfMonth() : -1;
    }

    public String getDate() {
        return date != null ? date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Invalid Date";
    }

    @Override
    public String toString() {
        return getDate();
    }

    public LocalDate getLocalDate() {
        return date;
    }
}