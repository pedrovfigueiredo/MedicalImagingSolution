package basic;

import java.io.Serializable;

public abstract class User implements Serializable{
	private String name;
	private String id;
	
	public User() {
		this.name = new String();
		this.id = new String();
	}
	
	public User(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
