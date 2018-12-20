package micro.api.example.controller;

import java.util.HashMap;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import micro.api.example.microservice.EntityMicroservice;

//TODO create configurator to configure the MicoserviceController, Microservice, and other classes

public class MicroserviceController implements IMicroserviceController {
	private JSONObject jsonRequestObject;
	private JSONObject jsonResponseObject;
	
	public MicroserviceController(String requestModel) {
		this.jsonResponseObject = new JSONObject();
		
		JSONParser parser = new JSONParser();
		JSONObject requestModelObject;
		
		try {
			requestModelObject = (JSONObject) parser.parse(requestModel);			
			this.jsonRequestObject = requestModelObject;
		} catch (ParseException e) {
			System.out.println("Unable to parse the request model!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public JSONObject execute() {
		JSONObject jsonRequest = (JSONObject) this.jsonRequestObject;		
		JSONObject actions = (JSONObject) jsonRequest.get("method");
		
		try {
			//TODO set up transactions for cases that require rollback
			
			//TODO if other types of non-CRUD microservices exist, create MircoserviceFactory
			if(actions.containsKey("get")) { 
				JSONObject getObject = (JSONObject) actions.get("get"); 
				this.routeToMicroservice("get",getObject); }		
			if(actions.containsKey("update")) { 
				JSONObject updateObject = (JSONObject) actions.get("update");
				this.routeToMicroservice("update",updateObject); }		
			if(actions.containsKey("post")) { 
				JSONObject postObject = (JSONObject) actions.get("post");
				this.routeToMicroservice("post",postObject); }		
			if(actions.containsKey("delete")) { 
				JSONObject deleteObject = (JSONObject) actions.get("delete");
				this.routeToMicroservice("delete",deleteObject); }
									
			return this.jsonResponseObject;
		} catch(Exception e) {
			System.out.println("The controller failed to execute the request!");
			return this.jsonResponseObject;
		}		
	}
	
	private void routeToMicroservice(String method, JSONObject methodObject) {
		//TODO create a Router class and use that instead
		Set entityNames = methodObject.keySet(); 		
		
		for(Object entityName : entityNames) {			
			
			if(methodObject.get(entityName) instanceof JSONObject) {
				JSONObject entityObject = (JSONObject) methodObject.get(entityName);				
				EntityMicroservice microService = new EntityMicroservice(entityObject);
				
				//TODO create a response object
				if(method=="get") { 
					this.jsonResponseObject.put(entityName, microService.read(entityName.toString())); }		
				if(method=="update") { 
					this.jsonResponseObject.put(entityName, microService.update(entityName.toString())); }		
				if(method=="post") {
					JSONArray entityArray = new JSONArray();
					this.jsonResponseObject.put(entityName, entityArray.add(microService.create(entityName.toString()))); }		
				if(method=="delete") { 
					this.jsonResponseObject.put(entityName, microService.delete(entityName.toString())); }
			} else if(methodObject.get(entityName) instanceof JSONArray) {
				
				for(Object entityObject : (JSONArray) methodObject.get(entityName)) {
					EntityMicroservice microService = new EntityMicroservice(entityObject);
					
					//TODO create a response object
					if(method=="get") { 
						this.jsonResponseObject.put(entityName, microService.read(entityName.toString())); }		
					if(method=="update") { 
						this.jsonResponseObject.put(entityName, microService.update(entityName.toString())); }		
					if(method=="post") {
						JSONArray entityArray = new JSONArray();
						entityArray.add(microService.create(entityName.toString()));
						this.jsonResponseObject.put(entityName,entityArray); }		
					if(method=="delete") { 
						this.jsonResponseObject.put(entityName, microService.delete(entityName.toString())); }
				}
				
			} else {
				//TODO throw exception
			}						
		}
		System.out.println("b: " + this.jsonResponseObject.toJSONString());
	}	
}
