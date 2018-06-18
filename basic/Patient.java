package basic;

import java.io.Serializable;

public class Patient extends User implements Serializable{
	private static final long serialVersionUID = 1L;
	private long birthdate;
	
	public Patient(String name, long date, String id) {
		super(name,id);
		this.birthdate = date;
	}
	
	public long getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(long birthdate) {
		this.birthdate = birthdate;
	}	
}
