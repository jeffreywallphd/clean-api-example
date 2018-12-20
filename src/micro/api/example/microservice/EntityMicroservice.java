package micro.api.example.microservice;

import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public JSONObject create(String entityName) {		
		EntityFactory factory = new EntityFactory();
		Entity entity = null;
			
		JSONObject responseObject = new JSONObject();
		
		//ensure request includes list of entity attributes
		if(this.jsonActionObject.get("attributes") == null) {
			System.out.println("The request entity is missing attributes!");
			return responseObject;
		} 
		
		//create requested entity object
		try {
            entity = factory.newEntity(entityName);			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("The requested entity does not exist within the system!");
			return responseObject;
		}
		
		//validate the entity attributes and data
		try {
			//validate that requested entity fields exist in entity object to prevent injection attacks
			JSONObject attributes = (JSONObject) this.jsonActionObject.get("attributes");			
			Set<String> attributeNames = attributes.keySet();
			
			for(String attribute : attributeNames) {
				if(!entity.hasAttribute(attribute)) {
					System.out.println("Cannot perform request. The requested field (" + attribute + ") does not exist on " + entityName);
					return responseObject;
				}
			}
			
			//validate data
			entity.populateAndValidate(this.jsonActionObject); 		
		} catch(Exception e) {
			//TODO add data validation error
			System.out.println("Failed to validate the entity data!");
			return responseObject;
		}
		
		//connect to entity data gateway
		MySQLEntityGateway gateway;
		
		try {
			//TODO inject connection string
			gateway = new MySQLEntityGateway("jdbc:mysql://localhost:3306","MicroserviceTest");
			gateway.connect();			
		} catch(Exception e) {
			System.out.println("Failed to connect to the database!");
			//TODO add database connection error
			return responseObject;
		}
		
		//process data results
		gateway.setEntity(entityName);		
		ResultSet results;
		
		try {
			results = (ResultSet) gateway.create(entity,this.jsonActionObject);
			results.next();
			int newEntityId = results.getInt(1);
			
			try {												
				responseObject.put("id", newEntityId);
				responseObject.put("code", 201);
				responseObject.put("message", "Created");
				
				//check for embedded entities
				Set properties = this.jsonActionObject.keySet();			
				
				for(Object property: properties) {
					//process embedded entities
					try {										
						if(property.equals("attributes") || property.equals("filters")) {						
							//ignore attributes and filters
						} else {
							JSONArray dataArray = new JSONArray();
							
							if(this.jsonActionObject.get(property) instanceof JSONArray) {
								JSONArray embeddedArray = (JSONArray) this.jsonActionObject.get(property);
																						
								for(Object embeddedEntity: embeddedArray) {				
									EntityMicroservice embeddedMicroService = new EntityMicroservice(embeddedEntity);
									dataArray.add(embeddedMicroService.create(property.toString()));
								}
							
							} else if(this.jsonActionObject.get(property) instanceof JSONObject) {
								EntityMicroservice embeddedMicroService = new EntityMicroservice(this.jsonActionObject.get(property));
								dataArray.add(embeddedMicroService.create(property.toString()));
							}
							
							responseObject.put(property,dataArray);
						}
					} catch(Exception e1) {
						//TODO add embedded entity error
						System.out.println("Failed to create embedded entity " + e1.getMessage());
					}
				}			
					
				return responseObject;
			} catch(Exception e) {
				//TODO add database select error
				System.out.println("Failed to create a new entity! " + e.getMessage());
				return responseObject;
			}
		} catch (SQLException e2) {
			System.out.println("Failed to execute entity creation! " + e2.getMessage());
			// TODO add database select error
			return responseObject;	
		}											 								
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