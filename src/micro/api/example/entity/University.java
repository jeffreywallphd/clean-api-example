package micro.api.example.entity;

public class University extends Entity {
	private int universityId;
	private String universityName;
	
	private int id = this.universityId;
	
	public int getUniversityId() {
		return this.universityId;
	}
	
	public void setUniversityId(int fieldValue) {
		this.universityId = fieldValue;
	}
	
	public String getUniversityName() {
		return this.universityName;
	}
	
	public void setUniversityName(String fieldValue) {
		this.universityName = fieldValue;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int fieldValue) {
		this.id = fieldValue;
	}
		
	public University () {}
}
