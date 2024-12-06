package org.HospitalManagement.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class FileUtils {

    // Đọc đối tượng từ file XML
    public static Object readFromXML(String fileName, Class<?> clazz) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            File file = new File(fileName);
            if (file.exists()) {
                return unmarshaller.unmarshal(file);
            } else {
                System.err.println("File " + fileName + " không tồn tại!");
            }
        } catch (JAXBException e) {
            System.err.println("Lỗi khi đọc file XML: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Ghi đối tượng vào file XML
    public static <T> void writeToXML(String fileName, T data) {
        try {
            JAXBContext context = JAXBContext.newInstance(data.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // Format file đẹp

            File file = new File(fileName);
            marshaller.marshal(data, file);
        } catch (JAXBException e) {
            System.err.println("Lỗi khi ghi file XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
