package micro.api.example.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import micro.api.example.microservice.EntityMicroservice;

public class UniversityMicroserviceController implements IMicroserviceController {
	private JSONObject jsonRequestObject;
	private JSONObject jsonResponseObject;
	
	public UniversityMicroserviceController(String requestModel) {
		this.jsonResponseObject = new JSONObject();
		
		JSONParser parser = new JSONParser();
		JSONObject requestModelObject;
		
		try {
			requestModelObject = (JSONObject) parser.parse(requestModel);			
			this.jsonRequestObject = requestModelObject;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public JSONObject execute() {
		JSONObject jsonRequest = (JSONObject) this.jsonRequestObject;		
		JSONObject actions = (JSONObject) jsonRequest.get("method");
		
		//TODO if other types of microservices exist, create MircoserviceFactory
		if(actions.containsKey("get")) { this.routeToMicroservice("get",actions.get("get")); }		
		if(actions.containsKey("update")) { this.routeToMicroservice("update",actions.get("update")); }		
		if(actions.containsKey("post")) { this.routeToMicroservice("post",actions.get("post")); }		
		if(actions.containsKey("delete")) { this.routeToMicroservice("delete",actions.get("delete")); }
								
		return this.jsonResponseObject;
	}
	
	private void routeToMicroservice(String method, Object actions) {
		//TODO create a Router class and use that instead
		JSONArray actionsArray = (JSONArray) actions;		
		
		for(Object action : actionsArray) {			
			JSONObject actionObject = (JSONObject) action;
			
			EntityMicroservice microService = new EntityMicroservice(actionObject);
			
			//TODO create a response object
			if(method=="get") { this.jsonResponseObject.put("toFix", microService.read()); }		
			if(method=="update") { this.jsonResponseObject.put("toFix", microService.update()); }		
			if(method=="post") { this.jsonResponseObject.put("toFix", microService.create()); }		
			if(method=="delete") { this.jsonResponseObject.put("toFix", microService.delete()); }
		}
	}	
}
