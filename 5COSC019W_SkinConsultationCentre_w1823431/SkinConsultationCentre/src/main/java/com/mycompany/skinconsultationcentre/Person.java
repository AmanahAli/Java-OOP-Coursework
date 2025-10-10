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
public class Person {
    private String name;
    private String surname;
     private LocalDate dob;
    private String mobileNum;
    
    public Person(String name, String surname, LocalDate dob, String mobileNum){
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.mobileNum = mobileNum;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setSurname(String surname){
        this.surname = surname;
    }
    
    public String getSurname(){
        return surname;
    }
    
    public void setDob(LocalDate dob){
        this.dob = dob;
    }
    
    public LocalDate getDob(){
        return dob;
    }
    
    public void setMobileNum(String mobileNum){
        this.mobileNum = mobileNum;
    }
    
    public String getMobileNum(){
        return mobileNum;
    }
    
    @Override
    public String toString(){
        return "Name: " + name + ", surname: " + surname + ", dob: " + dob + ", Mobile: " + mobileNum;
    } 
}
