package micro.api.example.entity;

import java.util.HashMap;

public class Person extends Entity {
	//class attributes	
	private int personId;
	private String firstName;
	private String lastName;
	private int universityId;	
	
	public int personId() {
		return this.personId;
	}
	
	public void personId(int fieldValue) {
		this.personId = fieldValue;
	}
	
	public String firstName() {
		return this.firstName;
	}
	
	public void firstName(String fieldValue) {
		this.firstName = fieldValue;
	}
	
	public String lastName() {
		return this.lastName;
	}
	
	public void lastName(String fieldValue) {
		this.lastName = fieldValue;
	}
	
	public int universityId() {
		return this.universityId;
	}
	
	public void universityId(int fieldValue) {
		this.universityId = fieldValue;
	}
	
	//call foreign key by name of foreign entity
	public int University() {
		return this.universityId();
	}
	
	public void University(int fieldValue) {
		this.universityId(fieldValue);
	}
	
	public int id() {
		return this.personId;
	}
	
	public void id(int fieldValue) {
		this.personId = fieldValue;
	}
	
	//constructor
	public Person() {
		this.primaryKey = "personId";
	}
}
