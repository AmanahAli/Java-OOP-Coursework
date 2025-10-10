# Skin Consultation Centre Management System

A **Java application** that manages doctors, patients, and consultations at a skin consultation centre. The system provides both a **console-based menu** and a **graphical user interface (GUI)** for interacting with the data. This project was developed as part of a coursework assignment to demonstrate skills in **object-oriented programming, GUI development, file handling, and encryption**.

## Features

**Doctor Management**

- Add, delete, and view doctors (max 10)
- Delete doctors by medical licence number
- View all doctors (sorted alphabetically by surname)
- Save/load doctor data to/from file

**Consultation Management**

- Book a consultation with a doctor
- Enter patient information (validated input)
- Calculate consultation cost
- Store encrypted notes (AES encryption)
- View consultation details through the GUI

**Graphical User Interface (GUI)**

- Doctor list with styled table and sorting
- Consultation booking panel: Interactive booking system with availability checking
- Consultation viewer panel: View all bookings with detailed information

**Data Persistence**

- All doctors and consultations are saved to file
- When the application restarts, data is loaded back automatically

**Error Handling & Validation**

- Validates mobile numbers
- Prevents adding more than 10 doctors
- Ensures correct date format (dd/MM/yyyy)
- Displays friendly error messages for invalid input

## Usage
**Console Menu Options**

1. **Add New Doctor:** Register doctors with validation for all fields
2. **Delete Doctor:** Remove doctors by medical licence number
3. **Print Doctor List:** Display all doctors sorted alphabetically
4. **Save and Load Data:** Manual data persistence operations
5. **Open GUI:** Launch the graphical interface
6. **Exit:** Save data and close application

**GUI Navigation**

- **View Doctors Tab:** View and sort doctor information
- **View Consultations Tab:** Browse all scheduled consultations
- **Book Consultation Tab:** Create new patient appointments

## Getting Started
# Prerequisites
- Java 8+ installed
- IDE such as IntelliJ IDEA, Eclipse, or NetBeans

# Running the Application
1. Clone this repository:
   git clone https://github.com/your-username/skin-consultation-centre.git
   cd skin-consultation-centre
2. Open the project in your IDE.
3. Run the main class:
   public static void main(String[] args) {
    WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager(10);
    manager.runMenu();  // Console menu
    // OR manager.openGUI();  // Open GUI directly
}


