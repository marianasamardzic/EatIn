package com.eatin.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.MeraDTO;
import com.eatin.dto.PrilogDTO;
import com.eatin.dto.artikl.ArtiklDTO;
import com.eatin.dto.artikl.ExtendedArtiklDTO;
import com.eatin.enums.SortByArtikl;
import com.eatin.jpa.Artikl;
import com.eatin.jpa.Mera;
import com.eatin.jpa.Moze_biti_mere;
import com.eatin.jpa.Moze_sadrzati_priloge;
import com.eatin.jpa.Prilog;
import com.eatin.repository.ArtiklRepository;
import com.eatin.repository.MozeBitiMereRepository;
import com.eatin.repository.MozeSadrzatiPrilogeRepository;

@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ArtiklController {

	@Autowired
	private ArtiklRepository artiklRepository;
	@Autowired
	private MozeBitiMereRepository mozeBitiMereRepository;
	@Autowired
	private MozeSadrzatiPrilogeRepository mozeSadrzatiPrilogeRepository;

	@GetMapping("artikl")
	public ResponseEntity<Page<ArtiklDTO>> getArtikl(@RequestParam(defaultValue = "1") @Min(1) int page,
			@RequestParam(defaultValue = "false") Boolean descending,
			@RequestParam(defaultValue = "ID") SortByArtikl sortBy, @RequestParam(required = false) String search,
			@RequestParam(required = false) Integer tipArtikla, @RequestParam(required = false) Integer restoran)
			throws Exception {

		Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(sortBy.label));
		if (descending) {
			pageable = PageRequest.of(page - 1, 5, Sort.by(sortBy.label).descending());
		}

		Page<Artikl> artikli;

		if (search == null) {
			search = "";
		}

		if (tipArtikla != null && restoran != null) {
			artikli = this.artiklRepository
					.findByTipArtikla_idTipaArtiklaAndRestoran_idRestoranaAndNazivArtiklaContainingIgnoreCase(
							tipArtikla, restoran, "", pageable);
		} else if (tipArtikla != null) {
			artikli = this.artiklRepository
					.findByTipArtikla_idTipaArtiklaAndNazivArtiklaContainingIgnoreCase(tipArtikla, search, pageable);
		} else if (restoran != null) {
			artikli = this.artiklRepository.findByRestoran_idRestoranaAndNazivArtiklaContainingIgnoreCase(restoran,
					search, pageable);
		} else {
			artikli = this.artiklRepository.findBynazivArtiklaContainingIgnoreCase(search, pageable);
		}

		Page<ArtiklDTO> responsePage = ObjectMapperUtils.mapPage(artikli, ArtiklDTO.class);
		return new ResponseEntity<Page<ArtiklDTO>>(responsePage, HttpStatus.OK);
	}

	@GetMapping("artikl/{id}")
	public ExtendedArtiklDTO getArtiklById(@PathVariable int id) {

		if (!this.artiklRepository.existsById(id)) {
			throw new EntityNotFoundException("Not found");
		}

		Artikl artiklEntity = this.artiklRepository.getOne(id);
		ExtendedArtiklDTO artikl = ObjectMapperUtils.map(artiklEntity, ExtendedArtiklDTO.class);

		// mere
		Collection<Moze_biti_mere> moze = this.mozeBitiMereRepository.findByArtikl_IdArtikla(artikl.getIdArtikla());
		List<Mera> mere = new ArrayList<>();

		Iterator<Moze_biti_mere> iterator = moze.iterator();
		while (iterator.hasNext()) {
			mere.add(iterator.next().getMera());
		}
		List<MeraDTO> mereDTO = ObjectMapperUtils.mapAll(mere, MeraDTO.class);
		artikl.setMere(mereDTO);

		// prilozi
		Collection<Moze_sadrzati_priloge> mozePrilozi = this.mozeSadrzatiPrilogeRepository
				.findByArtikl_IdArtikla(artikl.getIdArtikla());
		List<Prilog> prilozi = new ArrayList<>();
		Iterator<Moze_sadrzati_priloge> iteratorPrilozi = mozePrilozi.iterator();
		while (iteratorPrilozi.hasNext()) {
			prilozi.add(iteratorPrilozi.next().getPrilog());
		}
		List<PrilogDTO> priloziDTO = ObjectMapperUtils.mapAll(prilozi, PrilogDTO.class);
		artikl.setPrilozi(priloziDTO);

		return artikl;

	}
}
