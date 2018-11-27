package micro.api.example.test;

import static org.junit.Assert.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import micro.api.example.controller.IMicroserviceController;
import micro.api.example.controller.UniversityMicroserviceController;
import micro.api.example.microservice.EntityMicroservice;

public class MicroserviceTest {

	@Test
	public void shouldInstantiateMicroserviceController() {
		try {			
			String requestModel = "{\"method\": {\"get\":[{\"type\":\"University\",\"attributes\":[\"name\"],\"filter\": {\"contains\": {\"name\":\"Tech\"},\"limit\": 20,\"offset\": 0},\"relationships\": [{\"type\":\"President\",\"attributes\":[\"firstName\",\"lastName\"]}]},{\"type\":\"University\",\"attributes\":[\"name\"],\"filter\": {\"id\": 123}}],\"post\":[{\"type\":\"University\",\"data\":{\"name\":\"Michigan Tech\"},\"relationships\": [{\"type\":\"President\",\"data\":{\"firstName\":\"Rick\",\"lastName\":\"Koubek\"}}]}]}}";
			IMicroserviceController service = new UniversityMicroserviceController(requestModel);
		} catch(Exception e) {
			fail("Failed to instantiate the service controller for the following reason: " + e.getMessage());
		}		
	}
	
	@Test
	public void shouldCreateEntity() {
		//TODO not accepting arrays and later objects. Need to create a loop somewhere
		String requestModel = "[{\"name\":\"Michigan Tech\",\"President\":{\"firstName\":\"Rick\",\"lastName\":\"Koubek\"}}]";
		
		JSONParser parser = new JSONParser();
		JSONArray requestModelObject = null;
		
		try {			
			requestModelObject = (JSONArray) parser.parse(requestModel);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			fail("Failed to parse request model for the following reason: " + e.getMessage());
			e.printStackTrace();
		}
		
		EntityMicroservice microservice = new EntityMicroservice(requestModelObject);
		
		String responseModel = "{\"University\":{\"data\":[{\"code\":200,\"id\":1}],\"President\":[{\"data\":[{\"code\":200,\"id\":1}]}}";
		
		try {
			System.out.println(microservice.create("University").toString());
			assertEquals(responseModel,microservice.create("University").toString());
		}catch(Exception e) {			
			fail("Response does not match for the following reason: " + e.getMessage());
		}		
	}
	
	@Test
	public void shouldReadEntity() {
		String requestModel = "{\"attributes\":[\"name\"],\"filter\": {\"contains\": {\"name\":\"Tech\"},\"limit\": 20,\"offset\": 0},\"President\":{\"attributes\":[\"firstName\",\"lastName\"]}}";
				
		JSONParser parser = new JSONParser();
		JSONObject requestModelObject = null;
		
		try {			
			requestModelObject = (JSONObject) parser.parse(requestModel);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			fail("Failed to parse request model for the following reason: " + e.getMessage());
			e.printStackTrace();
		}
		
		EntityMicroservice microservice = new EntityMicroservice(requestModelObject);
		
		String responseModel = "{\"University\":[{}]}";
		
		try {
			System.out.println(microservice.read("University").toString());
			assertEquals(responseModel,microservice.read("University").toString());
		}catch(Exception e) {			
			fail("Response does not match for the following reason: " + e.getMessage());
		}
	}
	
	@Test
	public void shouldUpdateEntity() {
		
	}
	
	@Test
	public void shouldDeleteEntity() {
		
	}	
	
	@Test
	public void shouldPerformMethods() {
		try {
			String requestModel = "{\"method\": {\"get\":[{\"type\":\"University\",\"attributes\":[\"name\"],\"filter\": {\"contains\": {\"name\":\"Tech\"},\"limit\": 20,\"offset\": 0},\"relationships\": [{\"entity\":\"President\",\"attributes\":[\"firstName\",\"lastName\"]}]},{\"type\":\"University\",\"attributes\":[\"id\",\"name\"],\"filter\": {\"id\": 123}}],\"post\":[{\"entity\":\"type\",\"data\":{\"name\":\"Michigan Tech\"},\"relationships\": [{\"type\":\"President\",\"data\":{\"firstName\":\"Rick\",\"lastName\":\"Koubek\"}}]}]}}";														
			
			IMicroserviceController controller = new UniversityMicroserviceController(requestModel);
			JSONObject responseObject = controller.execute(); //TODO return response object
			
			String responseModel = "{\"response\": {\"get\":[{\"type\":\"University\",\"data\":[{\"name\":\"Michigan Tech\",\"relationsips\":[{\"type\":\"President\",\"data\":[{\"firstName\":\"Rick\",\"lastName\":\"Koubek\"}]}]}]},{\"type\":\"University\",\"data\":[{\"id\":123,\"name\":\"MIT\"}]}]},{\"post\":[{\"type\":\"President\",\"data\":[{\"id\":1,\"code\":200}]}]}}";
						
			System.out.println(responseModel.toString());
			assertEquals(responseModel,responseObject.toString());			
		} catch(Exception e) {
			System.out.println(e.getMessage());
			fail("Failed to perform the requested actions for the following reason: " + e.getMessage());
		}
	}
}
