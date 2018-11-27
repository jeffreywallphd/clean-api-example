package micro.api.example.entity;

public interface IPaginationFilter {
	public int offset();
	public void offset(int offset);
	
	public int limit();
	public void limit(int limit);
}
