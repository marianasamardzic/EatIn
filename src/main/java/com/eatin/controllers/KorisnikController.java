package com.eatin.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.korisnik.KorisnikDTO;
import com.eatin.repository.KorisnikRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class KorisnikController {

	@Autowired
	private KorisnikRepository korisnikRepository;

	@GetMapping("/korisnik")
	public Collection<KorisnikDTO> getAllKorisnik(@RequestParam(required = false) Integer uloga) {

		if (uloga != null) {
			return ObjectMapperUtils.mapAll(korisnikRepository.findByUloga_idUloge(uloga), KorisnikDTO.class);
		}

		return ObjectMapperUtils.mapAll(korisnikRepository.findAll(), KorisnikDTO.class);
	}
}
