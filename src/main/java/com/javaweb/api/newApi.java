package com.javaweb.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class newApi {
	
	@GetMapping(value = "/api/building/")
	public void example() {
		System.out.println("hi");
	}
}
