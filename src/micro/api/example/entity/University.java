package micro.api.example.entity;

import java.util.HashMap;

public class University extends Entity {
	//class attributes	
	private int id = this.universityId;
	private int universityId;
	private String name;
	
	private HashMap<String,String> dataTypes = new HashMap();
	
	//getter-setter methods
	public HashMap<String,String> getDataTypes() {		
		this.dataTypes.put("universityId", "int");
		this.dataTypes.put("name", "String");
		
		return this.dataTypes;
	}
	
	public int universityId() {
		return this.universityId;
	}
	
	public void universityId(int fieldValue) {
		this.universityId = fieldValue;
	}
	
	public String name() {
		return this.name;
	}
	
	public void universityName(String fieldValue) {
		this.name = fieldValue;
	}
	
	public int id() {
		return this.id;
	}
	
	public void id(int fieldValue) {
		this.id = fieldValue;
	}
	
	//constructor
	public University () {}
}
