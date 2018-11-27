package micro.api.example.microservice;

import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//import micro.api.example.api.DependencyManager;
import micro.api.example.entity.Entity;
import micro.api.example.entity.EntityFactory;
import micro.api.example.gateway.MySQLEntityGateway;

public class EntityMicroservice extends Microservice {
	//TODO inject dependencies
	
	private JSONObject jsonActionObject;
	
	public EntityMicroservice(Object actionObject) {
		super(actionObject);
		
		this.jsonActionObject = (JSONObject) actionObject;	
	}
	
	public Object read(String entityName) {
		EntityFactory factory = new EntityFactory();
		Entity entity = null;
					
		JSONObject responseObject = new JSONObject();
		
		try {
            entity = factory.newEntity(entityName);           
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			//TODO produce error and add to response Object
			return responseObject;
		}
		
		MySQLEntityGateway gateway;
		
		try {
			//TODO inject connection string
			String connectionString = "jdbc:mysql://localhost:3306/MicroserviceTest";
			
			//create gateway factory
			gateway = new MySQLEntityGateway();
			gateway.connect(connectionString);			
		} catch(Exception e) {
			//TODO add database connection error
			return responseObject;
		}		
		
		JSONObject databaseResults;
		
		try {
			databaseResults = gateway.read(this.jsonActionObject);
			responseObject.put(entityName,databaseResults);
		} catch(Exception e) {
			//TODO add database select error
			return responseObject;
		}
		
		try {
			entity.validate(this.jsonActionObject); //should throw exception if a field doesn't validate
		} catch(Exception e) {
			//TODO add data validation error
			return responseObject;
		}
		
		Set fieldNames = this.jsonActionObject.keySet();
		
		for(Object fieldName : fieldNames) {
			//anything not an attribute or filter is considered an embedded entity
			if(fieldName == "attributes" || fieldName == "filter") {
				continue;
			} else {
				EntityMicroservice microservice = null;
				
				JSONObject embeddedObject = (JSONObject) this.jsonActionObject.get(fieldName);
				
				try {
					microservice = new EntityMicroservice(embeddedObject);
					JSONObject embeddedObjectResponse = (JSONObject) microservice.read(fieldName.toString());
					responseObject.put(fieldName, embeddedObjectResponse);
				} catch(Exception e) {
					//TODO add error to response object
				}
			}
		}
		
		return responseObject;
	}

	public JSONObject create(String entityName) {		
		EntityFactory factory = new EntityFactory();
		Entity entity = null;
					
		JSONObject responseObject = new JSONObject();
		
		try {
            entity = factory.newEntity(entityName);			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return responseObject;
		}
		
		try {		
			entity.validate(this.jsonActionObject); //should throw exception if a field doesn't validate		
		} catch(Exception e) {
			//TODO add data validation error
			return responseObject;
		}
		
		MySQLEntityGateway gateway;
		
		try {
			//TODO inject connection string
			String connectionString = "jdbc:mysql://localhost:3306/MicroserviceTest";
			
			gateway = new MySQLEntityGateway();
			gateway.connect(connectionString);			
		} catch(Exception e) {
			//TODO add database connection error
			return responseObject;
		}
		
		try {		
			JSONObject databaseResults = gateway.create(entity,this.jsonActionObject);			
			responseObject.put(entityName, databaseResults);			
		} catch(Exception e) {
			//TODO add database select error
			return responseObject;
		}
						 						
		return responseObject;
	}
	
	public boolean update(String entityName) {
		System.out.println("trying to update object");
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean delete(String entityName) {
		System.out.println("trying to delete object");
		// TODO Auto-generated method stub
		return true;
	}	
}