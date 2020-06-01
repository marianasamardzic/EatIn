package com.eatin.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.ObjectMapperUtils;
import com.eatin.dto.ArtiklDTO;
import com.eatin.repository.ArtiklRepository;

@RestController
public class ArtiklController {

	@Autowired
	private ArtiklRepository artiklRepository;

	@GetMapping("artikl")
	public Collection<ArtiklDTO> getArtikl() {
		return ObjectMapperUtils.mapAll(this.artiklRepository.findAll(), ArtiklDTO.class);
	}
}
