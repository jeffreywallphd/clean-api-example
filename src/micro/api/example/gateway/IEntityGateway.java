package micro.api.example.gateway;

import org.json.simple.JSONObject;

import micro.api.example.entity.Entity;

public interface IEntityGateway {
	public void connect(String connectionString);
	public void disconnect();
	public Object create(Entity entity, JSONObject queryData);
	public Object read(JSONObject queryData);
	public Object update(JSONObject queryData);
	public Object delete(JSONObject queryData);
}
