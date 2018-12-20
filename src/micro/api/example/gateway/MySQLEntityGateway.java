package micro.api.example.gateway;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import micro.api.example.entity.Entity;

public class MySQLEntityGateway implements IEntityGateway {
	private Connection connection;
	private String database;
	private String entityName;
	private String host;
	
	public MySQLEntityGateway(String host, String database) {
		this.host = host;
		this.database = database;
	}
	
	@Override
	public void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			System.out.println(e2.getMessage());
			e2.printStackTrace();
		}

		Connection conn = null;
		
		try {
			String connectionString = this.host + "/" + this.database;
			this.connection = DriverManager.getConnection(connectionString,"testuser", "password");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}		
	}

	@Override
	public void disconnect() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setEntity(String table) {
		this.entityName = table;
	}

	@Override
	public Object create(Entity entity, JSONObject queryData) {
		String queryString = "";
		
		Field[] fields;
		ArrayList<String> fieldNamesArray = new ArrayList<String>();
		ArrayList<Object> fieldValuesArray = new ArrayList<Object>();
		
		try {
			queryString = "INSERT INTO " + this.entityName;
			
			String fieldsString = " (";
			String valuesString = " VALUES(";			
			
			JSONObject attributeData = (JSONObject) queryData.get("attributes");				
			
			fields = entity.getAttributes();
			
			int fieldCount = 0;
			
			for(int i=0; i<fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName().toString();
				
				if(!fieldName.equals(entity.primaryKey())) {
					if(fieldCount>0) {
						fieldsString += ",";
						valuesString += ",";
					}
										
					field.setAccessible(true);					
					
					Object fieldValue = field.get(entity);
					
					System.out.println(field.getName().toString()+" has value of: " + fieldValue);
					
					fieldNamesArray.add(fieldName);
					fieldValuesArray.add(fieldValue);
					
					fieldsString += "`" + fieldName + "`";
					valuesString += "?";
					
					fieldCount++;
				}
			}
			
			fieldsString += ")";
			valuesString += ")";
			
			queryString += fieldsString + valuesString;
			System.out.println("query string: "+queryString);
		} catch(Exception e) {
			//TODO throw exception for failing reflection
			System.out.println("Error: "+e.getMessage());
			return false;
		}
		
		PreparedStatement createEntity = null;
		
		try {
			createEntity = this.connection.prepareStatement(queryString,Statement.RETURN_GENERATED_KEYS);
						
			int parameterIteration = 1;
						
			for(int i=0; i<fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName().toString();
				
				if(!fieldName.equals(entity.primaryKey())) {
					//TODO set to data type of particular field
					
					field.setAccessible(true);
					
					String fieldType = field.getGenericType().toString();
					
					System.out.println(field.getName().toString());
					
					if(fieldType.equals("class java.lang.String")) {
						System.out.println("String is: " + fieldValuesArray.get(i).toString());
						createEntity.setString(parameterIteration, fieldValuesArray.get(i).toString()); }					
					else if(fieldType.equals("int")) {
						System.out.println("Integer is: " + (int) fieldValuesArray.get(i));
						createEntity.setInt(parameterIteration, (int) fieldValuesArray.get(i)); } 
					else {
						System.out.println(field.getName().toString() + " is of an unsupported data type");
						return false;
					}
					//TODO add other data types					
					
					parameterIteration++;
				}
			}
			
			createEntity.execute();
			//this.connection.commit();
			
			ResultSet results = createEntity.getGeneratedKeys();
			
			return results;			
		} catch (SQLException e) {
			System.out.println("Failed to execute the insert query!");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
	}

	@Override
	public JSONObject read(JSONObject queryData) {
		// TODO Auto-generated method stub
		Statement stmt;
		
		try {
			stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM University");  
			
			while(rs.next()) { 
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
			}
			
			this.connection.close(); 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		
		return null;
	}

	@Override
	public boolean update(JSONObject queryData) {
		// TODO Auto-generated method stub
		Statement stmt;
		
		try {
			stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("INSERT INTO University VALUES(null,'Michigan Tech'");  
			
			while(rs.next()) { 
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
			}
			
			this.connection.close();
			
			return true;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
			return false;
		}  
	}

	@Override
	public boolean delete(JSONObject queryData) {
		// TODO Auto-generated method stub
		Statement stmt;
		
		try {
			stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("INSERT INTO University VALUES(null,'Michigan Tech'");  
			
			while(rs.next()) { 
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
			}
			
			this.connection.close();
			
			return true;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}  
	}

}
