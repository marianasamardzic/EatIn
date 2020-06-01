package com.eatin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.ObjectMapperUtils;
import com.eatin.dto.ArtiklDTO;
import com.eatin.jpa.Artikl;
import com.eatin.repository.ArtiklRepository;

@RestController
public class ArtiklController {

	@Autowired
	private ArtiklRepository artiklRepository;

	@GetMapping("artikl")
	public Page<ArtiklDTO> getArtikl(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "false") Boolean descending,
			@RequestParam(defaultValue = "idArtikla") String sort) {
		Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(sort));
		if (descending) {
			pageable = PageRequest.of(page - 1, 5, Sort.by(sort).descending());
		}
		Page<Artikl> artikli = this.artiklRepository.findAll(pageable);

		return ObjectMapperUtils.mapPage(artikli, ArtiklDTO.class);
	}
}

/*
 * public Page<ArtiklDTO> getAllArtikl(@RequestParam(defaultValue = "1") int
 * page,
 * 
 * @RequestParam(defaultValue = "idArtikla") String sort,
 * 
 * @RequestParam(defaultValue = "false") Boolean
 * descending, @RequestParam(required = false) String search) {
 * 
 * Pageable firstPageWithTwoElements = PageRequest.of(page - 1, 5,
 * Sort.by(sort)); if (descending) { firstPageWithTwoElements =
 * PageRequest.of(page - 1, 5, Sort.by(sort).descending()); } Page<Artikl>
 * artikli; if (search != null) { artikli =
 * this.artiklRepository.findBynazivArtiklaContainingIgnoreCase(search,
 * firstPageWithTwoElements); } else { artikli =
 * this.artiklRepository.findAll(firstPageWithTwoElements); }
 * 
 * int totalNoPages = artikli.getTotalPages(); System.out.println(totalNoPages);
 * List<ArtiklDTO> dto = ObjectMapperUtils.mapAll(artikli.getContent(),
 * ArtiklDTO.class);
 * 
 * return ObjectMapperUtils.mapPage(artikli, ArtiklDTO.class);
 * 
 * }
 */