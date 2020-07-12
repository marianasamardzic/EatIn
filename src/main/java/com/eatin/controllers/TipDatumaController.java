package com.eatin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.restoran.TipDatumaDTO;
import com.eatin.jpa.Tip_datuma;
import com.eatin.repository.TipDatumaRepository;

@RestController
public class TipDatumaController {

	@Autowired
	private TipDatumaRepository tipDatumaRepository;

	@GetMapping("tip-datuma")
	public ResponseEntity<List<TipDatumaDTO>> getAllTipDatuma() {
		List<Tip_datuma> tipDatuma = this.tipDatumaRepository.findAll();
		List<TipDatumaDTO> dto = ObjectMapperUtils.mapAll(tipDatuma, TipDatumaDTO.class);
		return new ResponseEntity<List<TipDatumaDTO>>(dto, HttpStatus.OK);
	}
}
