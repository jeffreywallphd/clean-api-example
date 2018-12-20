package micro.api.example.entity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.json.simple.JSONObject;

public abstract class Entity {
	protected String primaryKey;
	
	public String primaryKey() {
		return this.primaryKey;
	}
	
	public boolean populateAndValidate(JSONObject requestObject) {
		try {
			//determine valid fields within entity
			Field[] fields = this.getAttributes();
			
			ArrayList<String> allowedFields = new ArrayList<String>();
			
			for(Field field: fields) {
				String fieldName = field.getName().toString();
				
				if(!fieldName.equals(primaryKey) & !fieldName.equals(this.primaryKey())) {
					allowedFields.add(fieldName);					
				}
			}
			
			//invoke setter methods for each field
			Method[] methods = this.getClass().getMethods();
			
			JSONObject attributes = (JSONObject) requestObject.get("attributes");
						
			for(Method method: methods) {
				
				if(allowedFields.contains(method.getName().toString()) && method.getParameterCount()>0) {																	
					method.invoke(this, attributes.get(method.getName().toString()));
				}
			}
			
			return true;
		} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			System.out.println("Failed to call the entity method for population and validation! " + e.getMessage());
			return false;
		}		
	}
	
	public boolean hasAttribute(String attribute) {
		try {
			this.getClass().getMethod(attribute);
			return true;
		} catch(NoSuchMethodException | SecurityException e) {
			return false;	
		}			
	}
	
	public Field[] getAttributes() {
		try {
			return this.getClass().getDeclaredFields();
		} catch(SecurityException e) {
			return null;
		}		
	}
} 
