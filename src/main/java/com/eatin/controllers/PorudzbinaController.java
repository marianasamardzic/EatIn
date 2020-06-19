package com.eatin.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.PorudzbinaDTO;
import com.eatin.dto.PrilogDTO;
import com.eatin.dto.SadrziDTO;
import com.eatin.enums.StatusPorudzbine;
import com.eatin.jpa.Ima_priloge;
import com.eatin.jpa.Prilog;
import com.eatin.repository.ImaPrilogeRepository;
import com.eatin.repository.PorudzbinaRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Validated
public class PorudzbinaController {

	@Autowired
	private PorudzbinaRepository porudzbinaRepository;
	@Autowired
	private ImaPrilogeRepository imaPrilogeRepository;

	@GetMapping("porudzbina")
	public ResponseEntity<Page<PorudzbinaDTO>> getAllPorudzbina(@RequestParam(defaultValue = "1") @Min(1) int page,
			@RequestParam(required = false) StatusPorudzbine statusPorudzbine) {

		Pageable pageable = PageRequest.of(page - 1, 5);

		Page<PorudzbinaDTO> porudzbineDTO;
		if (statusPorudzbine != null) {
			porudzbineDTO = ObjectMapperUtils.mapPage(
					this.porudzbinaRepository.findByStatusPorudzbineIgnoreCase(statusPorudzbine.label, pageable),
					PorudzbinaDTO.class);
		} else {
			porudzbineDTO = ObjectMapperUtils.mapPage(this.porudzbinaRepository.findAll(pageable), PorudzbinaDTO.class);
		}

		// prolazak kroz svaku porudzbinu
		Iterator<PorudzbinaDTO> iterator = porudzbineDTO.getContent().iterator();
		while (iterator.hasNext()) {
			PorudzbinaDTO porudzbina = iterator.next();

			// prolazak kroz svaku stavku porudzbine
			List<SadrziDTO> sadrzi = porudzbina.getSadrzis();
			Iterator<SadrziDTO> iteratorSadrzi = sadrzi.iterator();
			while (iteratorSadrzi.hasNext()) {

				// dodavanje priloga u stavku
				SadrziDTO stavka = iteratorSadrzi.next();

				Collection<Ima_priloge> imaPriloge = this.imaPrilogeRepository
						.findBySadrzi_idSadrzi(stavka.getIdSadrzi());
				List<Prilog> prilozi = new ArrayList<>();
				Iterator<Ima_priloge> imaPrilogeIterator = imaPriloge.iterator();
				while (imaPrilogeIterator.hasNext()) {
					prilozi.add(imaPrilogeIterator.next().getPrilog());
				}
				List<PrilogDTO> priloziDTO = ObjectMapperUtils.mapAll(prilozi, PrilogDTO.class);
				stavka.setPrilozi(priloziDTO);

			}
		}
		return new ResponseEntity<Page<PorudzbinaDTO>>(porudzbineDTO, HttpStatus.OK);
	}
}
