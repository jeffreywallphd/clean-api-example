package micro.api.example.api;

import java.util.ArrayList;
import java.util.HashMap;

public class DependencyManager {
	private HashMap<String,ArrayList<Dependency>> dependencies;
	
	public void add(String type, Dependency dependency) {
		ArrayList<Dependency> dependencyArray = new ArrayList<Dependency>();
		
		dependencyArray.add(dependency);
		
		this.dependencies.put(type, dependencyArray);
	}
	
	public ArrayList<Dependency> get(String type) {
		return this.dependencies.get(type);
	}
	
	public ArrayList<Dependency> getDependencyByType(String type) {
		return dependencies.get(type);
	}
}
