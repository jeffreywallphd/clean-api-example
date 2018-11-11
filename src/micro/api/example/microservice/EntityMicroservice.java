package micro.api.example.microservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;

//import micro.api.example.api.DependencyManager;
import micro.api.example.entity.Entity;
import micro.api.example.entity.EntityFactory;

public class EntityMicroservice extends Microservice {
	//protected DependencyManager dependencies;
	
	/*public Microservice(DependencyManager dependencies) {
		this.dependencies = dependencies;
	}*/
	
	private JSONObject jsonActionObject;
	
	public EntityMicroservice(JSONObject jsonActionObject) {
		super(jsonActionObject);
		this.jsonActionObject = jsonActionObject;
	}
	
	public Object read() {
		System.out.println("trying to read object");
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject create() {
		System.out.println("trying to create object");
		EntityFactory factory = new EntityFactory();
		
		//TODO create a connection manager to handle this
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			System.out.println(e2.getMessage());
			e2.printStackTrace();
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/MicroserviceTest","testuser", "password");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}
		
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from emp");  
			
			while(rs.next()) { 
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
			}
			
			conn.close(); 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		 
		
		JSONObject responseObject = new JSONObject();
		
		Entity entity = null;
		
		try {
            entity = factory.newEntity(this.jsonActionObject.get("type").toString());			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return responseObject;
		}
		
		return responseObject;
	}
	
	public boolean update() {
		System.out.println("trying to update object");
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean delete() {
		System.out.println("trying to delete object");
		// TODO Auto-generated method stub
		return true;
	}	
}
