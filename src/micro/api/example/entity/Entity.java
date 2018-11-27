package micro.api.example.entity;

import java.util.HashMap;

import org.json.simple.JSONObject;

public abstract class Entity {
	public void validate(JSONObject requestData) {
		requestData.get("data");
	}
	
	public abstract HashMap<String,String> getDataTypes();
} 
