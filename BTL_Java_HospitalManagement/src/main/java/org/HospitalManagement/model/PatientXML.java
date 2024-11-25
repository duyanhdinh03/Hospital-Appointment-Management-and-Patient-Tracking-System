package org.HospitalManagement.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;


@XmlRootElement(name = "patients")
@XmlAccessorType(XmlAccessType.FIELD)
public class PatientXML implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Patient> patients;

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
