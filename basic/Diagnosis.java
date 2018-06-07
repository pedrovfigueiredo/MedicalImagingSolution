package basic;

import java.awt.Image;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class Diagnosis implements Serializable{
	private ArrayList<Image> images;
	private Date timestamp;
	private Patient patient;
	private Physician physician;
	
	public Diagnosis(ArrayList<Image> images, Date timestamp, Patient patient, Physician physician) {
		super();
		this.images = images;
		this.timestamp = timestamp;
		this.patient = patient;
		this.physician = physician;
	}
	
	@Override
	public String toString() {
		String string = "Time: " + timestamp.toString() + "\n" +
						"Pacient: " + patient.getName() + "\n" +
						"Physician: " + physician.getName() + "\n" +
						"Image count: " + images.size() + "\n";
		return string;
	}

	public ArrayList<Image> getImages() {
		return images;
	}

	public void setImages(ArrayList<Image> images) {
		this.images = images;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Physician getPhysician() {
		return physician;
	}

	public void setPhysician(Physician physician) {
		this.physician = physician;
	}
}
