package com.eatin.controllers;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.artikl.TipArtiklaDTO;
import com.eatin.dto.artikl.TipArtiklaNoIdDTO;
import com.eatin.dto.restoran.TipRestoranaDTO;
import com.eatin.dto.restoran.TipRestoranaNoIdDTO;
import com.eatin.error.CustomException;
import com.eatin.jpa.Tip_artikla;
import com.eatin.jpa.Tip_restorana;
import com.eatin.repository.TipArtiklaRepository;

import io.swagger.annotations.ApiOperation;

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
	
	@PostMapping("tip-artikla-admin")
	@ApiOperation("Kreira novi tip artikla")
	public ResponseEntity<TipArtiklaNoIdDTO> addTipArtikla(@RequestBody TipArtiklaNoIdDTO tip) {
		Tip_artikla tipEntity = new Tip_artikla();
		tipEntity.setOpisTipaArtikla(tip.getOpisTipaArtikla());
		this.tipArtiklaRepository.save(tipEntity);
		return new ResponseEntity<TipArtiklaNoIdDTO>(tip, HttpStatus.CREATED);
	}
	
	@PutMapping("tip-artikla-admin")
	@ApiOperation("Menja tip artikla")
	public ResponseEntity<TipArtiklaDTO> updateTipArtikla(@RequestBody TipArtiklaDTO tip) throws Exception {
		Optional<Tip_artikla> entity = this.tipArtiklaRepository.findById(tip.getIdTipaArtikla());
		if (!entity.isPresent()) {
			throw new CustomException("Ne postoji tip artikla sa zadatim id-jem");
		}
		entity.get().setOpisTipaArtikla(tip.getOpisTipaArtikla());
		this.tipArtiklaRepository.save(entity.get());
		return new ResponseEntity<TipArtiklaDTO>(tip, HttpStatus.OK);
	}
}
