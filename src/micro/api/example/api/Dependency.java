package micro.api.example.api;

public class Dependency extends Object {
	private String name;
	private Object dependency;
	
	public Dependency(String name, Object dependency) {
		this.name = name;
		this.dependency = dependency;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDependency(Object dependency) {
		this.dependency = dependency;
	}
	
	public Object getDependency() {
		return this.dependency;
	}
}
