package micro.api.example.entity;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.json.simple.JSONObject;

public abstract class Entity {
	protected String primaryKey;
	
	public String primaryKey() {
		return this.primaryKey;
	}
	
	public void validate(JSONObject requestData) {
		requestData.get("data");
	}
	
	public boolean hasAttribute(String attribute) {
		try {
			this.getClass().getMethod(attribute);
			return true;
		} catch(Exception e) {
			return false;	
		}			
	}
	
	public Field[] getAttributes() {
		try {
			return this.getClass().getDeclaredFields();
		} catch(Exception e) {
			return null;
		}		
	}
} 
