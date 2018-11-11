package micro.api.example.entity;

public class University extends Entity {
	//class attributes
	private int universityId;
	private String universityName;
	
	private int id = this.universityId;
	
	//getter-setter methods
	public int universityId() {
		return this.universityId;
	}
	
	public void universityId(int fieldValue) {
		this.universityId = fieldValue;
	}
	
	public String universityName() {
		return this.universityName;
	}
	
	public void universityName(String fieldValue) {
		this.universityName = fieldValue;
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
