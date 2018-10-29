import static org.junit.Assert.*;

import org.junit.Test;

import micro.api.example.entity.University;

public class EntityTest {

	@Test
	public void shouldInstantiateEntity() {
		try {
			University uni = new University();
		} catch(Exception e) {
			fail("Failed to instantiate the entity for the following reason: " + e.getMessage());
		}		
	}
	
}
