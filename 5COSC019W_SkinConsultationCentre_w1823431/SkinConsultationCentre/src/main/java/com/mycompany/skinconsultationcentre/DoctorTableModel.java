/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.skinconsultationcentre;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Amanah
 */
public class DoctorTableModel extends AbstractTableModel {
    private final String[] columnNames = {"First Name", "Surname", "DOB", "Mobile Number", "Medical Licence No", "Specialism"};
    private final ArrayList<Doctor> doctors;

    public DoctorTableModel(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    @Override
    public int getRowCount(){
        return doctors.size();
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    // Returns the value to display in each cell
    @Override
    public Object getValueAt(int row, int col){
        Doctor doctor = doctors.get(row);
        return switch (col) {
            case 0 -> doctor.getName();
            case 1 -> doctor.getSurname();
            case 2 -> doctor.getDob();
            case 3 -> doctor.getMobileNum();
            case 4 -> doctor.getMedLicenceNum();
            case 5 -> doctor.getJobSpecialism();
            default -> null;
        };
    }
}
        