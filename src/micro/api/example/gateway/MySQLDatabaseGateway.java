package micro.api.example.gateway;

import org.json.simple.JSONObject;

public class MySQLDatabaseGateway implements ISQLDatabaseGateway{

	@Override
	public void connect(String connectionString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollBack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object insert(JSONObject queryData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object select(JSONObject queryData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object update(JSONObject queryData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object delete(JSONObject queryData) {
		// TODO Auto-generated method stub
		return null;
	}

}
