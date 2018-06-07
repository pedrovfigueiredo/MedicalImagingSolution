package basic;

import java.sql.Date;

public class Patient extends User{
	private Date birthdate;
	
	public Patient(String name, Date birthdate, String id) {
		super(name,id);
		this.birthdate = birthdate;
	}
	
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}	
}
