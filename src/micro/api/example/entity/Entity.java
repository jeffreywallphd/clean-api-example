package micro.api.example.entity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.json.simple.JSONObject;

public abstract class Entity {
	protected String[] protectedMethods = {"id","populateAndValidate","hasAttribute","getAttributes","primaryKey"};
	
	protected String primaryKey;	
	public String primaryKey() {
		return this.primaryKey;
	}
	
	public abstract int id();	
	public abstract void id(int fieldValue);
	
	public boolean populateAndValidate(JSONObject requestObject) {
		ArrayList<String> protectedMethods = new ArrayList(); //TODO find a way to use whitelist instead of blacklist
		//protected entity methods
		protectedMethods.add("id");
		protectedMethods.add("populateAndValidate");
		protectedMethods.add("hasAttribute");
		protectedMethods.add("getAttributes");
		protectedMethods.add("primaryKey");
		//protected object methods
		protectedMethods.add("wait");
		protectedMethods.add("equals");
		protectedMethods.add("toString");
		protectedMethods.add("hashCode");
		protectedMethods.add("getClass");
		protectedMethods.add("notify");
		protectedMethods.add("notifyAll");
		
		try {
			//invoke setter methods for each field
			Method[] methods = this.getClass().getMethods();
			
			JSONObject attributes = (JSONObject) requestObject.get("attributes");
			
			System.out.println(attributes.toJSONString());
			
			for(Method method: methods) {
				System.out.println("The current method is: "+method.getName().toString());
				if(!protectedMethods.contains(method.getName().toString()) 
						&& method.getParameterCount()>0 
						&& attributes.get(method.getName().toString()) != null) {
					System.out.println("method name is: "+method.getName().toString());
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
