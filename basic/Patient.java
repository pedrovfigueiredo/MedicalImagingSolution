package basic;

import java.util.Date;

public class Patient extends User{
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
