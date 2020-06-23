package com.eatin.controllers;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.porudzbina.PorudzbinaDTO;
import com.eatin.enums.StatusPorudzbine;
import com.eatin.jpa.Porudzbina;
import com.eatin.repository.ImaPrilogeRepository;
import com.eatin.repository.KlijentRepository;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.PorudzbinaRepository;
import com.eatin.repository.StavkaPorudzbineRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Validated
public class PorudzbinaController {

	@Autowired
	private PorudzbinaRepository porudzbinaRepository;
	@Autowired
	private ImaPrilogeRepository imaPrilogeRepository;
	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private KlijentRepository klijentRepository;
	@Autowired
	private StavkaPorudzbineRepository stavkaPorudzbineRepository;

	@GetMapping("porudzbina")
	public ResponseEntity<Page<PorudzbinaDTO>> getAllPorudzbina(@RequestParam(defaultValue = "1") @Min(1) int page,
			@RequestParam(required = false) StatusPorudzbine statusPorudzbine) {

		Pageable pageable = PageRequest.of(page - 1, 5);

		Page<PorudzbinaDTO> porudzbineDTO;
		Page<Porudzbina> entiteti;
		if (statusPorudzbine != null) {
			entiteti = this.porudzbinaRepository.findByStatusPorudzbineIgnoreCase(statusPorudzbine.label, pageable);
		} else {
			entiteti = this.porudzbinaRepository.findAll(pageable);
		}
		porudzbineDTO = ObjectMapperUtils.mapPage(entiteti, PorudzbinaDTO.class);

		return new ResponseEntity<Page<PorudzbinaDTO>>(porudzbineDTO, HttpStatus.OK);
	}



	@GetMapping("porudzbina/{id}")
	public ResponseEntity<PorudzbinaDTO> getOnePorudzbinaById(@PathVariable int id) {

		Porudzbina porudzbina = this.porudzbinaRepository.getOne(id);
		PorudzbinaDTO porudzbineDTO = ObjectMapperUtils.map(porudzbina, PorudzbinaDTO.class);

		return new ResponseEntity<PorudzbinaDTO>(porudzbineDTO, HttpStatus.OK);
	}



}
