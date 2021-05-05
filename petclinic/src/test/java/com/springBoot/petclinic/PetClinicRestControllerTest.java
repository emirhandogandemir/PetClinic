package com.springBoot.petclinic;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.springBoot.petclinic.model.Owner;

public class PetClinicRestControllerTest {
	private RestTemplate restTemplate;

	@Before
	public void setUp() {
		restTemplate = new RestTemplate();
	}
	

	@Test
	public void testGetOwnerById() {
		ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Emirhan"));
	}

	@Test
	public void testCreateOwner() {
		Owner owner = new Owner();
		owner.setFirstName("samet");
		owner.setLastName("yılmaz");
		
	URI location =	restTemplate.postForLocation("http://localhost:8080/rest/owner", owner);
	Owner owner2 = restTemplate.getForObject(location,Owner.class );
	MatcherAssert.assertThat(owner2.getFirstName(), Matchers.comparesEqualTo(owner.getFirstName()));
	MatcherAssert.assertThat(owner2.getLastName(), Matchers.comparesEqualTo(owner.getLastName()));
	}
	@Test
	public void testUpdateOwner() {
		Owner owner = restTemplate.getForObject("http://localhost:8080/rest/owner/4",Owner.class);
		MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("Hasan"));
		
		owner.setFirstName("Hasan Osman");
		restTemplate.put("http://localhost:8080/rest/owner/4", owner);
		owner =restTemplate.getForObject("http://localhost:8080/rest/owner/4",Owner.class);
		MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("Hasan Osman"));
		
	}
	@Test
	public void testDeleteOwner() {
		restTemplate.delete("http://localhost:8080/rest/owner/3");
		try {
			restTemplate.getForEntity("http://localhost:8080/rest/owner/3", Owner.class);
			Assert.fail("Should have not returned owner");
		}catch(HttpClientErrorException ex) {
			MatcherAssert.assertThat(ex.getStatusCode().value(), Matchers.equalTo(404));
		}
		
		
	}
	
	
	
	
	@Test
	public void testGetOwnersByLastName() {
		ResponseEntity<List> response = restTemplate
				.getForEntity("http://localhost:8080/rest/owner?lastname=Doğandemir", List.class);

		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		List<Map<String, String>> body = response.getBody();

		List<String> firstName = body.stream().map(e -> e.get("firstName")).collect(Collectors.toList());
		MatcherAssert.assertThat(firstName, Matchers.containsInAnyOrder("Emirhan"));
	}
	@Test
	public void testGetOwners() {
		ResponseEntity<List> response=restTemplate.getForEntity("http://localhost:8080/rest/owners",List.class);
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		List<Map<String, String>> body = response.getBody();

		List<String> firstName = body.stream().map(e -> e.get("firstName")).collect(Collectors.toList());
		MatcherAssert.assertThat(firstName, Matchers.containsInAnyOrder("Emirhan","Ahmet","Okan","Hasan"));
	}
}
