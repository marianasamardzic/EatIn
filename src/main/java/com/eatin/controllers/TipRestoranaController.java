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
import com.eatin.dto.restoran.TipRestoranaDTO;
import com.eatin.repository.TipRestoranaRepository;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
}
