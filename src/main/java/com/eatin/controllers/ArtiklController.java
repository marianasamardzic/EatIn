package com.eatin.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtiklController {

	@GetMapping("artikl")
	public String getArtikl() {
		return "artikl";
	}
}
