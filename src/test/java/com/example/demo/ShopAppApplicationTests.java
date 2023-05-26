package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopAppApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("Hello");
		assertEquals("1", "1");
	}

}
