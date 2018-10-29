package micro.api.example.interactor;

import java.util.ArrayList;

import org.json.simple.JSONArray;

import micro.api.example.controller.DependencyObject;
import micro.api.example.entity.Entity;

public abstract class Interactor implements InputBoundaryObject {
	protected DependencyObject dependencies;
	
	public Interactor(DependencyObject dependencies) {
		this.dependencies = dependencies;
	}
	
	abstract public Entity create(Object entityObject);
	abstract public ArrayList<Entity> read();
	abstract public boolean update();
	abstract public boolean delete();
}
