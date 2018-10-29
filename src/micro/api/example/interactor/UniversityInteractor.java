package micro.api.example.interactor;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import micro.api.example.controller.DependencyObject;
import micro.api.example.entity.Entity;
import micro.api.example.entity.University;

public class UniversityInteractor extends Interactor {	
	public UniversityInteractor(DependencyObject dependencies) {
		super(dependencies);
		// TODO Auto-generated constructor stub
	}
	
	public Entity create(Object entityObject) {
		University entity = new University();
		
		
		// TODO implement entity creation
		// TODO call to database gateway to create
		
		return entity;
	}

	public ArrayList<Entity> read() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean update() {
		// TODO Auto-generated method stub
		
		return false;
	}

	public boolean delete() {
		// TODO Auto-generated method stub
		
		return false;
	}
}
