package org.HospitalManagement.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "patients")
@XmlAccessorType(XmlAccessType.FIELD)
public class PatientXML {

    @XmlElement(name = "patient")
    private List<Patient> patient;

    public List<Patient> getPatient() {
        return patient ;
    }

    public void setPatient(List<Patient> patient) {
        this.patient = patient;
    }
}
