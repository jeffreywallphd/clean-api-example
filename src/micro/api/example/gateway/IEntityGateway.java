package micro.api.example.gateway;

import org.json.simple.JSONObject;

import micro.api.example.entity.Entity;

public interface IEntityGateway {
	public void connect();
	public void disconnect();
	public Object create(Entity entity, JSONObject queryData);
	public Object read(JSONObject queryData);
	public boolean update(JSONObject queryData);
	public boolean delete(JSONObject queryData);
}
