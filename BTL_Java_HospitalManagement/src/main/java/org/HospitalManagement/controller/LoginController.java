package org.HospitalManagement.controller;

import org.HospitalManagement.dao.UserDAO;
import org.HospitalManagement.model.User;
import org.HospitalManagement.view.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private UserDAO userDAO;
    private LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        this.userDAO = new UserDAO();
        // Đăng ký sự kiện cho nút Login
        this.loginView.addLoginListener(new LoginListener());
    }

    public void showLoginView() {
        loginView.setVisible(true);
    }

    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            // Kiểm tra dữ liệu đầu vào
            if (username.isEmpty() || password.isEmpty()) {
                loginView.showMessage("Username và Password không được để trống!", true);
                return;
            }

            // Kiểm tra thông tin người dùng
            User user = userDAO.getUserByUsername(username);
            if (user == null) {
                loginView.showMessage("Tên đăng nhập không tồn tại!", true);
                return;
            }

            // Kiểm tra mật khẩu
            if (!password.equals(user.getPassword())) {  // Nếu sử dụng mã hóa mật khẩu, thay đổi logic này
                loginView.showMessage("Mật khẩu không đúng!", true);
                return;
            }

            // Đăng nhập thành công, điều hướng theo vai trò
            loginView.showMessage("Đăng nhập thành công với vai trò: " + user.getRole(), false);
            navigateToRoleView(user);
        }
    }

    private void navigateToRoleView(User user) {
        switch (user.getRole()) {
            case "Admin":
                // Mở giao diện quản trị viên
                break;
            case "Patient":
                // Mở giao diện bệnh nhân
                break;
        }
    }
}
