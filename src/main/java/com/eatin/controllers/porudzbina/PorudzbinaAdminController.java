package com.eatin.controllers.porudzbina;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.porudzbina.PorudzbinaDTO;
import com.eatin.enums.StatusPorudzbine;
import com.eatin.jpa.Porudzbina;
import com.eatin.repository.PorudzbinaRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@Validated
public class PorudzbinaAdminController {

	@Autowired
	private PorudzbinaRepository porudzbinaRepository;

	@ApiOperation("Izlistava sve porudzbine")
	@GetMapping("porudzbina")
	public ResponseEntity<Page<PorudzbinaDTO>> getAllPorudzbina(@RequestParam(defaultValue = "1") @Min(1) int page,
			@RequestParam(required = false) StatusPorudzbine statusPorudzbine) {

		// paginacija
		Pageable pageable = PageRequest.of(page - 1, 5);

		// izvlacenje iz baze
		Page<Porudzbina> entiteti;
		if (statusPorudzbine != null) {
			entiteti = this.porudzbinaRepository.findByStatusPorudzbineIgnoreCase(statusPorudzbine.label, pageable);
		} else {
			entiteti = this.porudzbinaRepository.findAll(pageable);
		}

		// mapiranje
		Page<PorudzbinaDTO> porudzbineDTO = ObjectMapperUtils.mapPage(entiteti, PorudzbinaDTO.class);

		return new ResponseEntity<Page<PorudzbinaDTO>>(porudzbineDTO, HttpStatus.OK);
	}

	@ApiOperation("Izlistava porudzbinu sa datim id-jem")
	@GetMapping("porudzbina/{id}")
	public ResponseEntity<PorudzbinaDTO> getOnePorudzbinaById(@PathVariable int id) throws EntityNotFoundException {

		// provera
		if (!this.porudzbinaRepository.existsById(id)) {
			throw new EntityNotFoundException("Could not find porudzbina with id " + id);
		}

		// izvlacenje iz baze
		Porudzbina porudzbina = this.porudzbinaRepository.getOne(id);

		// mapiranje
		PorudzbinaDTO porudzbineDTO = ObjectMapperUtils.map(porudzbina, PorudzbinaDTO.class);

		return new ResponseEntity<PorudzbinaDTO>(porudzbineDTO, HttpStatus.OK);
	}

}
