package com.example.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.Service.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
	@Autowired
	UserService userService;
	@Autowired
	private ObjectMapper objectMapper;
	
	
	private static final Logger log = LoggerFactory.getLogger(UserTest.class);

	
	/*@Test
	public void findUserByUsername() {
		User user = userService.findUserByUsername("yangye");
		System.out.println(user);
	}
	
	@Test
	public void findAll() {
		List<User> users = userService.findAll();
		System.out.println(users.size());
	}*/
	
	/*@Test
	public void updateU_PById() {
		int count = userService.updateU_PById(4, "tanghaoyu","939633875");
		System.out.println(count);
	}
	*/

	
	@Test
	public void testPage() {//page表示第几页
		String map = "{\"page\" : 1,\"pageSize\" :8, \"filter\":{ \"filters\":[{ \"field\" : \"username\", \"value\":\"yangye\"}]}}";
		Map searchParameters = new HashMap();
		try {
			searchParameters = objectMapper.readValue(map, new TypeReference<Map>() {
			});
		} catch (JsonParseException e) {
			log.error("JsonParseException{}:", e.getMessage());
		} catch (JsonMappingException e) {
			log.error("JsonMappingException{}:", e.getMessage());
		} catch (IOException e) {
			log.error("IOException{}:", e.getMessage());
		}

		Map mapUser = userService.getPage(searchParameters);

		System.out.println(mapUser.get("total"));

		System.out.println(mapUser.get("users"));
	}
}
