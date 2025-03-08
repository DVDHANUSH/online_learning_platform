package com.elearn.app.start_learn_back;

import com.elearn.app.start_learn_back.config.Security.JwtUtil;
import com.elearn.app.start_learn_back.services.CategoryService;
import jdk.jfr.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StartLearnBackApplicationTests {
	@Test
	void contextLoads() {
	}
	@Autowired
	private JwtUtil jwtUtil;

	@Test
	public void testJWT(){
		System.out.println("testing jwt");
		String token = jwtUtil.generateToken("DV Dhanush");
		System.out.println("the token generated : " + token);
		System.out.println(jwtUtil.validateToken(token, "DV Dhanush"));
		System.out.println(jwtUtil.extractUsername(token));
	}
}