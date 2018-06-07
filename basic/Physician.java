package basic;

public class Physician extends User {
	
	private String institution;
	
	public Physician(String name, String id, String institution) {
		super(name, id);
		this.institution = institution;
	}
	
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
}
