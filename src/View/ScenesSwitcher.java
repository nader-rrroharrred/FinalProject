/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import Controller.Admin.AdminLoginController;
import Controller.Admin.PatientLoginController;
import Controller.Admin.PatientRegisterController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Nader
 */
public class ScenesSwitcher extends Stage {
    Scene loginPatientScene;
    Scene loginAdminScene;
    Scene registerScene;
    Scene adminDashboard;
    Scene appointments;
    Scene bookedAppointments;
    Scene newPatient;
    Scene newAppointment;
    Scene patientDashboard;
    Scene myAppointments;

    // Load Login Page Scene
    public ScenesSwitcher() throws IOException {
        // Load Login Page Scene
        FXMLLoader PatientLoginloader = new FXMLLoader(getClass().getResource("AdminFXML/PatientLogin.fxml"));
        Parent PatientLoginroot = PatientLoginloader.load();
        PatientLoginController controller = PatientLoginloader.getController();
        loginPatientScene = new Scene(PatientLoginroot);
        
        // Load AdminLogin Page Scene
        FXMLLoader AdminLoginloader = new FXMLLoader(getClass().getResource("AdminFXML/AdminLogin.fxml"));
        Parent AdminLoginroot = AdminLoginloader.load();
        AdminLoginController controlleradmin = AdminLoginloader.getController();
        loginAdminScene = new Scene(AdminLoginroot);
        
        // Load Register Page Scene
        FXMLLoader Registerloader = new FXMLLoader(getClass().getResource("AdminFXML/PatientRegister.fxml"));
        Parent Registerloaderroot = Registerloader.load();
        registerScene = new Scene(Registerloaderroot);
        
        // Load Admin Dashboard Page Scene
        FXMLLoader adminDashboardloader = new FXMLLoader(getClass().getResource("AdminFXML/DoctorDashboard.fxml"));
        Parent adminDashboardloaderroot = adminDashboardloader.load();
        adminDashboard = new Scene(adminDashboardloaderroot);
        // Appointments Page Scene
        FXMLLoader appointmentsloader = new FXMLLoader(getClass().getResource("AdminFXML/AppointmentsPage.fxml"));
        Parent appointmentsloaderroot = appointmentsloader.load();
        appointments = new Scene(appointmentsloaderroot);
        // BookedAppointments Page Scene
        FXMLLoader bookedAppointmentsloader = new FXMLLoader(getClass().getResource("AdminFXML/BookedAppointments.fxml"));
        Parent bookedAppointmentsloaderloaderroot = bookedAppointmentsloader.load();
        bookedAppointments = new Scene(bookedAppointmentsloaderloaderroot);
        // BookedAppointments Page Scene
        FXMLLoader newPatientsloader = new FXMLLoader(getClass().getResource("AdminFXML/NewPatient.fxml"));
        Parent newPatientloaderloaderroot = newPatientsloader.load();
        newPatient = new Scene(newPatientloaderloaderroot);
        // newAppointment Page Scene
        FXMLLoader newAppointmentloader = new FXMLLoader(getClass().getResource("AdminFXML/NewAppointment.fxml"));
        Parent newAppointmentloaderroot = newAppointmentloader.load();
        newAppointment = new Scene(newAppointmentloaderroot);
        
        // Load Patient Dashboard Page Scene
        FXMLLoader patientDashboardloader = new FXMLLoader(getClass().getResource("AdminFXML/PatientDashboard.fxml"));
        Parent patientDashboardloaderroot = patientDashboardloader.load();
        patientDashboard = new Scene(patientDashboardloaderroot);
        
        // Load My Appointments Page Scene
        FXMLLoader myAppointmentsloader = new FXMLLoader(getClass().getResource("AdminFXML/MyAppointments.fxml"));
        Parent myAppointmentsroot = myAppointmentsloader.load();
        myAppointments = new Scene(myAppointmentsroot);
        
        
    }

    public void openLogin() {
        this.setScene(loginPatientScene);
        this.setTitle("Login");
        this.show();
    }
    public void openAdmin(){
        this.setScene(loginAdminScene);
        this.setTitle("LoginAdmin");
        this.show();
    }
    public void openRegister(){
        this.setScene(registerScene);
        this.setTitle("Register");
        this.show();
    }
    public void openAdminDashboard(){
        this.setScene(adminDashboard);
        this.setTitle("Doctor Dashboard");
        this.show();
    }
    public void openAppointments(){
        this.setScene(appointments);
        this.setTitle("Appointments");
        this.show();
    }
    public void openBookedAppointments(){
        this.setScene(bookedAppointments);
        this.setTitle("Booked Appointments");
        this.show();
    }
    public void openNewPatient(){
        this.setScene(newPatient);
        this.setTitle("New Patient");
        this.show();
    }
    public void openNewAppointment(){
        this.setScene(newAppointment);
        this.setTitle("New Appointment");
        this.show();
    }
    
    public void openPatientDashboard(){
        this.setScene(patientDashboard);
        this.setTitle("Patient Dashboard");
        this.show();
    }
    
    public void openMyAppointments(){
        this.setScene(myAppointments);
        this.setTitle("My Appointments");
        this.show();
    }
    
    
    public void close(){
         this.close();
    }

  

}
