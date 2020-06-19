package com.eatin.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.TipRestoranaDTO;
import com.eatin.repository.TipRestoranaRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Validated
@RestController
public class TipRestoranaController {
	@Autowired
	private TipRestoranaRepository tipRestoranaRepository;

	@GetMapping("tip-restorana")
	public ResponseEntity<Collection<TipRestoranaDTO>> getTipRestorana() {

		List<TipRestoranaDTO> tipoviRestoranaDTO = ObjectMapperUtils.mapAll(this.tipRestoranaRepository.findAll(),
				TipRestoranaDTO.class);

		return new ResponseEntity<Collection<TipRestoranaDTO>>(tipoviRestoranaDTO, HttpStatus.OK);
	}
}
