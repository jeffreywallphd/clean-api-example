package micro.api.example.gateway;

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
	private String entityName;
		
	@Override
	public void connect(String connectionString) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			System.out.println(e2.getMessage());
			e2.printStackTrace();
		}

		Connection conn = null;
		
		try {
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

	@Override
	public JSONObject create(Entity entity, JSONObject queryData) {
		PreparedStatement createEntity = null;
		
		String queryString = "INSERT INTO ? ";
		
		String fieldsString = "(";
		String valuesString = " VALUES(";
		
		ArrayList<String> fieldNamesArray = new ArrayList<String>();
		ArrayList<Object> fieldValuesArray = new ArrayList<Object>();
		
		JSONObject attributeData = (JSONObject) queryData.get("attributes");		
		
		Set<String> fieldNames = attributeData.keySet();	
		Iterator<String> iterator = fieldNames.iterator();
		
		for(String fieldName : fieldNames) {
			fieldNamesArray.add(fieldName.toString());
			fieldValuesArray.add(attributeData.get(fieldName));
			
			fieldsString += "?";
			valuesString += "?";
			
			if(iterator.hasNext()) {
				fieldsString += ",";
				valuesString += ",";
			}
		}
		
		fieldsString += ")";
		valuesString += ")";
		
		queryString += fieldsString + valuesString;
		
		try {
			createEntity = this.connection.prepareStatement(queryString);
			
			createEntity.setString(1,this.entityName);
			
			int parameterIteration = 2;
			
			for(int i=0; i<fieldNamesArray.size(); i++) {
				createEntity.setString(parameterIteration, fieldNamesArray.get(i).toString());
				parameterIteration++;
			}
			
			for(int j=0; j<fieldValuesArray.size(); j++) {
				//TODO set to data type of particular field
				HashMap<String,String> attributeDataTypes = entity.getDataTypes();
				
				if(attributeDataTypes.containsKey(fieldNamesArray.get(j).toString())) {
					String dataType = attributeDataTypes.get(fieldNamesArray.get(j).toString());
					
					if(dataType == "String") {
						createEntity.setString(parameterIteration, fieldValuesArray.get(j).toString()); }					
					else if(dataType == "int") {
						createEntity.setInt(parameterIteration, (int) fieldValuesArray.get(j)); }
					//TODO add other data types					
					
					parameterIteration++;
				}				
			}
			
			System.out.println(queryString);
			
			createEntity.execute();
			this.connection.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		// TODO Auto-generated method stub
		return null;
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
	public Object update(JSONObject queryData) {
		// TODO Auto-generated method stub
		Statement stmt;
		
		try {
			stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("INSERT INTO University VALUES(null,'Michigan Tech'");  
			
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
	public Object delete(JSONObject queryData) {
		// TODO Auto-generated method stub
		Statement stmt;
		
		try {
			stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("INSERT INTO University VALUES(null,'Michigan Tech'");  
			
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

}
