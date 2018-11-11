package micro.api.example.entity;

public class EntityFactory {
	public Entity newEntity(String entityName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String fullEntityName = "micro.api.example.entity." + entityName;
		
		return (Entity) Class.forName(fullEntityName).newInstance();
	}
}
