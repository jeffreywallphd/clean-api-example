package micro.api.example.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import micro.api.example.controller.IMicroserviceController;
import micro.api.example.controller.MicroserviceController;
import micro.api.example.microservice.EntityMicroservice;

public class MicroserviceTest {

	public void resetTestDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			fail("MySQL Driver for Test Database Not Available: " + e2.getMessage());
		}

		Connection conn = null;
		
		try {
			String connectionString = "jdbc:mysql://localhost:3306/MicroserviceTest";
			conn = DriverManager.getConnection(connectionString,"testuser", "password");
		} catch (SQLException e1) {
			fail("No Test Database Available: " + e1.getMessage());
		}
		
		String query1 = "DELETE FROM Person;";
		String query2 = "ALTER TABLE Person AUTO_INCREMENT=1;";
		String query3 = "DELETE FROM University;";
		String query4 = "ALTER TABLE University AUTO_INCREMENT=1";
		
		try {
			Statement statement = conn.createStatement();
			statement.execute(query1);
			statement.execute(query2);
			statement.execute(query3);
			statement.execute(query4);
			conn.close();
		} catch (SQLException e1) {
			System.out.println("a: " + e1.getMessage());
			fail("Test Database Statement creation failed: " + e1.getMessage());
		}	
	}
	
	@Test
	public void shouldInstantiateMicroserviceController() {
		try {			
			String requestModel = "{\"method\":{\"post\":{\"University\":[{\"attributes\": {\"name\":\"Michigan Tech\"},\"Person\":[{\"attributes\": {\"firstName\":\"Rick\",\"lastName\":\"Koubek\"}},{\"attributes\": {\"firstName\":\"Tom\",\"lastName\":\"Brady\"}}]}]}}}";
			IMicroserviceController service = new MicroserviceController(requestModel);
		} catch(Exception e) {
			fail("Failed to instantiate the service controller for the following reason: " + e.getMessage());
		}		
	}
	
	@Test
	public void shouldExecuteMicroserviceController() {
		this.resetTestDB();
		
		try {			
			String requestModel = "{\"method\":{\"post\":{\"University\":[{\"attributes\": {\"name\":\"Michigan Tech\"},\"Person\":[{\"attributes\": {\"firstName\":\"Rick\",\"lastName\":\"Koubek\"}},{\"attributes\": {\"firstName\":\"Tom\",\"lastName\":\"Brady\"}}]}]}}}";
			IMicroserviceController service = new MicroserviceController(requestModel);
			
			String responseModel = "{\"University\":[{\"code\":201,\"id\":1,\"message\":\"Created\",\"Person\":[{\"code\":201,\"id\":1,\"message\":\"Created\"},{\"code\":201,\"id\":2,\"message\":\"Created\"}]}]}";
			
			String actualModel = service.execute().toJSONString();
			
			System.out.println("e: "+responseModel);
			System.out.println("a: "+actualModel);
			
			assertEquals(responseModel,actualModel);
		} catch(Exception e) {
			fail("Failed to instantiate the service controller for the following reason: " + e.getMessage());
		}		
	}
	
	@Test
	public void shouldCreateEntity() throws SQLException {
		this.resetTestDB();	
		
		//TODO not accepting arrays and later objects. Need to create a loop somewhere
		String requestModel = "{\"attributes\": {\"name\":\"Michigan Tech\"},\"Person\":[{\"attributes\": {\"firstName\":\"Rick\",\"lastName\":\"Koubek\"}},{\"attributes\": {\"firstName\":\"Tom\",\"lastName\":\"Brady\"}}]}";
		
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
		
		String responseModel = "{\"code\":201,\"id\":1,\"message\":\"Created\",\"Person\":[{\"code\":201,\"id\":1,\"message\":\"Created\"},{\"code\":201,\"id\":2,\"message\":\"Created\"}]}";
		String responseActual = microservice.create("University").toJSONString();
		
		try {
			assertEquals(responseModel,responseActual);
		}catch(Exception e) {
			fail("Response does not match for the following reason: " + e.getMessage());
		}		
	}
	
	//@Test
	public void shouldReadEntity() {
		String requestModel = "{\"attributes\":[\"name\"],\"filters\": {\"contains\": {\"name\":\"Tech\"},\"limit\": 20,\"offset\": 0},\"President\":{\"attributes\":[\"firstName\",\"lastName\"]}}";
				
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
	
	//@Test
	public void shouldUpdateEntity() {
		
	}
	
	//@Test
	public void shouldDeleteEntity() {
		
	}	
	
	//@Test
	public void shouldPerformMethods() {
		try {
			String requestModel = "{\"method\": {\"get\":[{\"type\":\"University\",\"attributes\":[\"name\"],\"filters\": {\"contains\": {\"name\":\"Tech\"},\"limit\": 20,\"offset\": 0},\"relationships\": [{\"entity\":\"President\",\"attributes\":[\"firstName\",\"lastName\"]}]},{\"type\":\"University\",\"attributes\":[\"id\",\"name\"],\"filters\": {\"id\": 123}}],\"post\":[{\"entity\":\"type\",\"data\":{\"name\":\"Michigan Tech\"},\"relationships\": [{\"type\":\"President\",\"data\":{\"firstName\":\"Rick\",\"lastName\":\"Koubek\"}}]}]}}";														
			
			IMicroserviceController controller = new MicroserviceController(requestModel);
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
