package org.HospitalManagement.utils;

import java.io.*;
import java.util.List;
import javax.swing.table.TableModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class FileUtils {

    // Export to CSV
    public static void exportToCSV(String filePath, TableModel tableModel) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write header
            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                writer.write(tableModel.getColumnName(col));
                if (col < tableModel.getColumnCount() - 1) {
                    writer.write(",");
                }
            }
            writer.newLine();

            // Write rows
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    writer.write(tableModel.getValueAt(row, col).toString());
                    if (col < tableModel.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }
        }
    }
    //Export to XML
    public static void exportToXML(String filePath, TableModel tableModel) throws IOException, TransformerException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element root = document.createElement("appointments");
        document.appendChild(root);

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            Element appointment = document.createElement("appointment");
            document.appendChild(root);

            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                Element element = document.createElement(tableModel.getColumnName(col));
                element.appendChild(document.createTextNode(tableModel.getValueAt(row, col).toString()));
                appointment.appendChild(element);
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }

    //export to docx
    public static void exportToDOC(String filePath, TableModel tableModel) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write header
            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                writer.write(tableModel.getColumnName(col) + "\t");
            }
            writer.newLine();
            writer.newLine();

            // Write rows
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    writer.write(tableModel.getValueAt(row, col).toString() + "\t");
                }
                writer.newLine();
            }
        }
    }

    //import from CSV
    public static List<String[]> importFromCSV(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String[]> data = new java.util.ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                data.add(line.split(","));
            }

            return data;
        }
    }

    //import from xml
    public static List<String[]> importFromXML(String filePath) throws Exception {
        List<String[]> data = new java.util.ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filePath));

        Element root = document.getDocumentElement();
        var appointments = root.getElementsByTagName("Appointment");

        for (int i = 0; i < appointments.getLength(); i++) {
            Element appointment = (Element) appointments.item(i);
            var children = appointment.getChildNodes();

            List<String> row = new java.util.ArrayList<>();
            for (int j = 0; j < children.getLength(); j++) {
                if (children.item(j) instanceof Element) {
                    row.add(children.item(j).getTextContent());
                }
            }
            data.add(row.toArray(new String[0]));
        }

        return data;
    }

    //Save file
    public static void saveFile(String format, TableModel tableModel) {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        int option = fileChooser.showSaveDialog(null);
        if (option == javax.swing.JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();

            if (!filePath.endsWith("." + format)) {
                filePath += "." + format;
            }

            try {
                switch (format.toLowerCase()) {
                    case "csv":
                        exportToCSV(filePath, tableModel);
                        break;
                    case "xml":
                        exportToXML(filePath, tableModel);
                        break;
                    case "doc":
                        exportToDOC(filePath, tableModel);
                        break;
                }

                javax.swing.JOptionPane.showMessageDialog(null, "File exported successfully to " + filePath);
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error exporting file: " + e.getMessage());
            }
        }
    }

}
