package micro.api.example.gateway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UniversityEntityJDBCMySQLGateway implements iEntityGateway {
	private Connection dbConnection;
	private Statement queryStatement;
	
	public void connect() {
		String user;
		String password;
		
		this.dbConnection=null;
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			this.dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306",user,password); 
		} catch(SQLException e) {
			// TODO implement exception handling
		}
	}
	
	public void create() {
		try {
			this.queryStatement = this.dbConnection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void read() {
		try {
			this.queryStatement = this.dbConnection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void update() {
		try {
			this.queryStatement = this.dbConnection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void delete() {
		try {
			this.queryStatement = this.dbConnection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
	}
}
