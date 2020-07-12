package com.eatin.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.mera.MeraDTO;
import com.eatin.dto.mera.MeraNoIdDTO;
import com.eatin.error.CustomException;
import com.eatin.jpa.Mera;
import com.eatin.repository.MeraRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class MeraController {

	@Autowired
	private MeraRepository meraRepository;

	@ApiOperation("Izlistava sve mere")
	@GetMapping("mera")
	public ResponseEntity<List<MeraDTO>> getAllMera() {
		List<MeraDTO> mere = ObjectMapperUtils.mapAll(this.meraRepository.findAll(), MeraDTO.class);
		return new ResponseEntity<List<MeraDTO>>(mere, HttpStatus.OK);
	}
	
	@ApiOperation("Kreira novu meru")
	@PostMapping("mera")
	public ResponseEntity<MeraDTO> createMera(@RequestBody MeraNoIdDTO mera) {
		Mera entity = new Mera();
		entity.setOpisMere(mera.getOpisMere());
		this.meraRepository.save(entity);
		return new ResponseEntity<MeraDTO>(ObjectMapperUtils.map(entity, MeraDTO.class), HttpStatus.CREATED);
	}

	@ApiOperation("Izmena mere")
	@PutMapping("mera/{id}")
	public ResponseEntity<MeraDTO> updateMera(@PathVariable int id, @RequestBody MeraNoIdDTO mera)
			throws Exception {
		Optional<Mera> entity = this.meraRepository.findById(id);
		if (!entity.isPresent()) {
			throw new CustomException("Mera sa zadatim id-jem ne postoji");
		}
		entity.get().setOpisMere(mera.getOpisMere());
		this.meraRepository.save(entity.get());
		return new ResponseEntity<MeraDTO>(ObjectMapperUtils.map(entity, MeraDTO.class), HttpStatus.OK);
	}
}
