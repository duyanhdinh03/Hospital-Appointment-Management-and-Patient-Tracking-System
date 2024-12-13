package org.HospitalManagement.view.doctor.staticsic;

import javax.swing.*;
import java.awt.*;
import org.HospitalManagement.utils.FileUtils;

public class ExportFormatForm extends JFrame {
    private JTable table;

    public ExportFormatForm(JTable table) {
        this.table = table;

        setTitle("Chọn Định Dạng Xuất");
        setSize(400, 250);
        setLayout(new GridLayout(4, 1, 10, 10)); // Tăng khoảng cách giữa các nút

        JButton csvButton = new JButton("Xuất CSV");
        JButton xmlButton = new JButton("Xuất XML");
        JButton docButton = new JButton("Xuất DOC");
        JButton backButton = new JButton("Quay Lại");

        csvButton.addActionListener(e -> exportFile("csv"));
        xmlButton.addActionListener(e -> exportFile("xml"));
        docButton.addActionListener(e -> exportFile("doc"));
        backButton.addActionListener(e -> dispose()); // Đóng form khi bấm "Quay Lại"

        add(csvButton);
        add(xmlButton);
        add(docButton);
        add(backButton);

        setLocationRelativeTo(null); // Căn giữa màn hình
    }

    private void exportFile(String format) {
        try {
            FileUtils.saveFile(format, table.getModel());
            JOptionPane.showMessageDialog(this, "Xuất file " + format.toUpperCase() + " thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
