package micro.api.example.test;

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
	
	@Test
	public void shouldFillEntityAttributes() {
		try {
			University uni = new University();
			uni.universityId(1);
			uni.universityName("Michigan Technological University");
			
			assertEquals(1,uni.universityId());
			assertEquals("Michigan Technological University",uni.universityName());
		} catch(Exception e) {
			fail("Failed to store entity attributes correctly for the following reason: " + e.getMessage());
		}
	}
}
