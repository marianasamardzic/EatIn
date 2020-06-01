package com.eatin.controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.TipArtiklaDTO;
import com.eatin.repository.TipArtiklaRepository;

@Validated
@RestController
public class TipArtiklaController {

	@Autowired
	private TipArtiklaRepository tipArtiklaRepository;

	@GetMapping("tip-artikla")
	public ResponseEntity<Collection<TipArtiklaDTO>> getTipArtikla(
			@RequestParam(required = false) @Min(0) Integer numberOfItems) {
		List<TipArtiklaDTO> tipoviArtiklaDTO = ObjectMapperUtils.mapAll(this.tipArtiklaRepository.findAll(),
				TipArtiklaDTO.class);

		if (numberOfItems != null) {
			for (TipArtiklaDTO tipArtikla : tipoviArtiklaDTO) {
				int size = tipArtikla.getArtikls().size();
				if (size > numberOfItems) {
					tipArtikla.getArtikls().subList(numberOfItems, size).clear();
				}
			}
		}

		return new ResponseEntity<Collection<TipArtiklaDTO>>(tipoviArtiklaDTO, HttpStatus.OK);
	}
}
