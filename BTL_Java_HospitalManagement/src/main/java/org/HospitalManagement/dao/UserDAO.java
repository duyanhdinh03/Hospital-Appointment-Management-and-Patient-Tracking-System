package org.HospitalManagement.dao;

import org.HospitalManagement.model.User;
import org.HospitalManagement.model.UserXML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDAO {
    private static final String USER_FILE_NAME = "users.xml";
    private List<User> users;

    public UserDAO() {
        this.users = readUsersFromFile();
    }

    // Đọc danh sách người dùng từ file XML
    public List<User> readUsersFromFile() {
        List<User> userList = new ArrayList<>();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(USER_FILE_NAME);
            if(inputStream != null) {
                JAXBContext context = JAXBContext.newInstance(UserXML.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                UserXML wrapper = (UserXML) unmarshaller.unmarshal(inputStream);
                userList = wrapper.getUsers();
            }
            else {
                System.err.println("File users.xml không được tìm thấy!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    // Lưu danh sách người dùng vào file XML
    public void writeUsersToFile() {
        try {
            JAXBContext context = JAXBContext.newInstance(UserXML.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            UserXML wrapper = new UserXML();
            wrapper.setUsers(users);
            marshaller.marshal(wrapper, new File(Objects.requireNonNull(getClass().getClassLoader().getResource(USER_FILE_NAME)).getFile()));
        } catch (Exception e) {
            System.err.println("Error writing user file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Tìm người dùng theo username
    public User getUserByUsername(String username) {
        System.out.println("Danh sách user hiện tại: " + users.size());
        for (User user : users) {
            System.out.println("Checking user: " + user.getUsername());
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // Cập nhật mật khẩu người dùng
    public boolean updateUserPassword(int userId, String newPassword) {
        Optional<User> optionalUser = users.stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst();

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(newPassword);
            writeUsersToFile();
            return true;
        } else {
            System.err.println("User with ID " + userId + " not found.");
            return false;
        }
    }

    // Thêm người dùng mới (kiểm tra trùng username)
    public void addUser(User user) {
        if (users.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
            System.err.println("Username already exists: " + user.getUsername());
            return;
        }
        users.add(user);
        writeUsersToFile();
    }

    // Xóa người dùng theo ID
    public boolean deleteUser(int userId) {
        boolean removed = users.removeIf(user -> user.getUserId() == userId);
        if (removed) {
            writeUsersToFile();
        } else {
            System.err.println("User with ID " + userId + " not found.");
        }
        return removed;
    }

    // Lấy danh sách người dùng theo vai trò
    public List<User> getUsersByRole(String role) {
        return users.stream()
                .filter(user -> user.getRole().equalsIgnoreCase(role))
                .collect(Collectors.toList());
    }

    // Lấy danh sách tất cả người dùng
    public List<User> getAllUsers() {
        return new ArrayList<>(users); // Trả về bản sao danh sách để tránh sửa đổi ngoài ý muốn
    }
}
