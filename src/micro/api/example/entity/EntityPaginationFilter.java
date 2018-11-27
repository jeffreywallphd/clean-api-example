package micro.api.example.entity;

public class EntityPaginationFilter implements IPaginationFilter {
	private int offset;
	private int limit;
	
	@Override
	public int offset() {
		return this.offset;
	}

	@Override
	public void offset(int offset) {
		this.offset = offset;
	}

	@Override
	public int limit() {
		return this.limit;
	}

	@Override
	public void limit(int limit) {
		this.limit = limit;
	}

}
