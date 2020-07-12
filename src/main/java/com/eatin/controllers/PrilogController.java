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
import com.eatin.dto.PrilogDTO;
import com.eatin.dto.PrilogNoIdDTO;
import com.eatin.dto.artikl.TipArtiklaDTO;
import com.eatin.dto.artikl.TipArtiklaNoIdDTO;
import com.eatin.error.CustomException;
import com.eatin.jpa.Prilog;
import com.eatin.jpa.Tip_artikla;
import com.eatin.repository.PrilogRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class PrilogController {
	
	@Autowired
	private PrilogRepository prilogRepository;

	@GetMapping("prilog")
	@ApiOperation("Izlistava sve priloge")
	public ResponseEntity<Collection<PrilogDTO>> getPrilog() {

		List<PrilogDTO> prilogDTO = ObjectMapperUtils.mapAll(this.prilogRepository.findAll(),
				PrilogDTO.class);

		return new ResponseEntity<Collection<PrilogDTO>>(prilogDTO, HttpStatus.OK);
	}
	
	@PostMapping("prilog")
	@ApiOperation("Kreira novi prilog")
	public ResponseEntity<PrilogNoIdDTO> addPrilog(@RequestBody PrilogNoIdDTO tip) {
		Prilog tipEntity = new Prilog();
		tipEntity.setNazivPriloga(tip.getNazivPriloga());
		this.prilogRepository.save(tipEntity);
		return new ResponseEntity<PrilogNoIdDTO>(tip, HttpStatus.CREATED);
	}
	
	@PutMapping("prilog")
	@ApiOperation("Menja prilog")
	public ResponseEntity<PrilogDTO> updatePrilog(@RequestBody PrilogDTO tip) throws Exception {
		Optional<Prilog> entity = this.prilogRepository.findById(tip.getIdPriloga());
		if (!entity.isPresent()) {
			throw new CustomException("Ne postoji prilog sa zadatim id-jem");
		}
		entity.get().setNazivPriloga(tip.getNazivPriloga());
		this.prilogRepository.save(entity.get());
		return new ResponseEntity<PrilogDTO>(tip, HttpStatus.OK);
	}
}
