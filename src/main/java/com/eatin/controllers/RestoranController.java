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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.LokacijaDTO;
import com.eatin.dto.restoran.RestoranDTO;
import com.eatin.enums.SortByRestoran;
import com.eatin.jpa.Lokacija;
import com.eatin.jpa.Restoran;
import com.eatin.jpa.Restoran_se_nalazi;
import com.eatin.repository.RestoranRepository;
import com.eatin.repository.RestoranSeNalaziRepository;

import io.swagger.annotations.ApiOperation;

//-obezbedjuje validaciju parametara
@Validated
@RestController
public class RestoranController {

	@Autowired
	private RestoranRepository restoranRepository;
	@Autowired
	private RestoranSeNalaziRepository restoranSeNalaziRepository;

	@ApiOperation("Izlistava sve restorane")
	@GetMapping("restoran")
	public ResponseEntity<Page<RestoranDTO>> getAllRestoran(@RequestParam(defaultValue = "1") @Min(1) int page,
			@RequestParam(defaultValue = "ID") SortByRestoran sortBy,
			@RequestParam(defaultValue = "false") Boolean descending,
			@RequestParam(required = false) String search,
			@RequestParam(required = false) Integer tipRestorana) {

		// paginacija i sortiranje
		Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(sortBy.label));
		if (descending) {
			pageable = PageRequest.of(page - 1, 5, Sort.by(sortBy.label).descending());
		}

		// pretraga
		if (search == null) {
			search = "";
		}

		// izvlacenje iz baze
		Page<Restoran> restorani;
		if (tipRestorana != null) {
			restorani = this.restoranRepository
					.findByjeTipa_tipRestorana_idTipaRestoranaAndNazivRestoranaContainingIgnoreCase(tipRestorana,
							search, pageable);
		} else {
			restorani = this.restoranRepository.findByNazivRestoranaContainingIgnoreCase(search, pageable);
		}

		// mapiranje u DTO
		Page<RestoranDTO> responsePage = ObjectMapperUtils.mapPage(restorani, RestoranDTO.class);

		Iterator<RestoranDTO> iterator = responsePage.getContent().iterator();
		while (iterator.hasNext()) {
			RestoranDTO restoran = iterator.next();

			Collection<Restoran_se_nalazi> nalazi = this.restoranSeNalaziRepository
					.findByRestoran_idRestorana(restoran.getIdRestorana());
			List<Lokacija> lokacije = new ArrayList<>();
			Iterator<Restoran_se_nalazi> nalaziIterator = nalazi.iterator();
			while (nalaziIterator.hasNext()) {
				lokacije.add(nalaziIterator.next().getLokacija());
			}

			List<LokacijaDTO> lokacijeDTO = ObjectMapperUtils.mapAll(lokacije, LokacijaDTO.class);
			restoran.setLokacije(lokacijeDTO);
		}

		// vracanje Response Entity-ja
		return new ResponseEntity<Page<RestoranDTO>>(responsePage, HttpStatus.OK);
	}

}
