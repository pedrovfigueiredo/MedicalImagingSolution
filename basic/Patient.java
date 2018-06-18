package basic;

import java.io.Serializable;
import java.util.Date;

public class Patient extends User implements Serializable{
	private static final long serialVersionUID = 1L;
	private Date birthdate;
	
	public Patient(String name, Date date, String id) {
		super(name,id);
		this.birthdate = date;
	}
	
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}	
}
