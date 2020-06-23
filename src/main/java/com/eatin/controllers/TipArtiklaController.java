package com.eatin.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.artikl.TipArtiklaDTO;
import com.eatin.repository.TipArtiklaRepository;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class TipArtiklaController {

	@Autowired
	private TipArtiklaRepository tipArtiklaRepository;

	@GetMapping("tip-artikla")
	@ApiOperation("Izlistava sve tipove artikala")
	public ResponseEntity<Collection<TipArtiklaDTO>> getTipArtikla() {

		List<TipArtiklaDTO> tipoviArtiklaDTO = ObjectMapperUtils.mapAll(this.tipArtiklaRepository.findAll(),
				TipArtiklaDTO.class);

		return new ResponseEntity<Collection<TipArtiklaDTO>>(tipoviArtiklaDTO, HttpStatus.OK);
	}
}
