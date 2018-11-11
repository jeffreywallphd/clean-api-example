package micro.api.example.gateway;

import org.json.simple.JSONObject;

public interface ISQLDatabaseGateway {
	public void connect(String connectionString);
	public void disconnect();
	public void rollBack();
	public Object insert(JSONObject queryData);
	public Object select(JSONObject queryData);
	public Object update(JSONObject queryData);
	public Object delete(JSONObject queryData);
}
