/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.skinconsultationcentre;


/**
 *
 * @author Amanah
 */
public class SkinConsultationCentre {

    public static void main(String[] args) {
        SkinConsultationManager sys = new WestminsterSkinConsultationManager(10);
        boolean exit = false;
        while (!exit){
            exit = sys.runMenu();
    }
}
}
   
        
