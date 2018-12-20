package micro.api.example.entity;

import java.util.HashMap;

public class University extends Entity {
	//class attributes	
	private int universityId;
	private String name;
	
	public int universityId() {
		return this.universityId;
	}
	
	public void universityId(int fieldValue) {
		this.universityId = fieldValue;
	}
	
	public String name() {
		return this.name;
	}
	
	public void name(String fieldValue) {
		this.name = fieldValue;
	}
	
	public int id() {
		return this.universityId;
	}
	
	public void id(int fieldValue) {
		this.universityId = fieldValue;
	}
	
	//constructor
	public University () {}
}
