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
import com.eatin.dto.restoran.TipRestoranaDTO;
import com.eatin.dto.restoran.TipRestoranaNoIdDTO;
import com.eatin.error.CustomException;
import com.eatin.jpa.Tip_restorana;
import com.eatin.repository.TipRestoranaRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class TipRestoranaController {

	@Autowired
	private TipRestoranaRepository tipRestoranaRepository;

	@GetMapping("tip-restorana")
	@ApiOperation("Izlistava sve tipove restorana")
	public ResponseEntity<Collection<TipRestoranaDTO>> getTipRestorana() {

		List<TipRestoranaDTO> tipoviRestoranaDTO = ObjectMapperUtils.mapAll(this.tipRestoranaRepository.findAll(),
				TipRestoranaDTO.class);

		return new ResponseEntity<Collection<TipRestoranaDTO>>(tipoviRestoranaDTO, HttpStatus.OK);
	}
	
	@PostMapping("tip-restorana-admin")
	@ApiOperation("Kreira novi tip restorana")
	public ResponseEntity<TipRestoranaNoIdDTO> addTipRestorana(@RequestBody TipRestoranaNoIdDTO tip) {
		Tip_restorana tipEntity = new Tip_restorana();
		tipEntity.setOpisTipaRestorana(tip.getOpisTipaRestorana());
		this.tipRestoranaRepository.save(tipEntity);
		return new ResponseEntity<TipRestoranaNoIdDTO>(tip, HttpStatus.CREATED);
	}

	@PutMapping("tip-restorana-admin")
	@ApiOperation("Menja tip restorana")
	public ResponseEntity<TipRestoranaDTO> updateTipRestorana(@RequestBody TipRestoranaDTO tip) throws Exception {
		Optional<Tip_restorana> entity = this.tipRestoranaRepository.findById(tip.getIdTipaRestorana());
		if (!entity.isPresent()) {
			throw new CustomException("Ne postoji tip restorana sa zadatim id-jem");
		}
		entity.get().setOpisTipaRestorana(tip.getOpisTipaRestorana());
		this.tipRestoranaRepository.save(entity.get());
		return new ResponseEntity<TipRestoranaDTO>(tip, HttpStatus.OK);
	}
}
