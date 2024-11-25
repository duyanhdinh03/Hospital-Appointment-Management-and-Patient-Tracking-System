package org.HospitalManagement;

import org.HospitalManagement.controller.LoginController;
import org.HospitalManagement.view.LoginView;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            LoginView loginView = new LoginView();
            LoginController controller = new LoginController(loginView);

            controller.showLoginView();
        });
    }
}