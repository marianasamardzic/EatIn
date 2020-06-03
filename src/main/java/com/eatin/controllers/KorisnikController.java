package com.eatin.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.KorisnikDTO;
import com.eatin.repository.KorisnikRepository;

@RestController
public class KorisnikController {

	@Autowired
	private KorisnikRepository korisnikRepository;

	@GetMapping("/korisnik")
	public Collection<KorisnikDTO> getAllKorisnik() {
		return ObjectMapperUtils.mapAll(korisnikRepository.findAll(), KorisnikDTO.class);
	}
}
