package com.eatin.controllers;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.ObjectMapperUtils;
import com.eatin.dto.ArtiklDTO;
import com.eatin.enums.SortBy;
import com.eatin.jpa.Artikl;
import com.eatin.repository.ArtiklRepository;

@Validated
@RestController
public class ArtiklController {

	@Autowired
	private ArtiklRepository artiklRepository;

	@GetMapping("artikl")
	public Page<ArtiklDTO> getArtikl(@RequestParam(defaultValue = "1") @Min(1) int page,
			@RequestParam(defaultValue = "false") Boolean descending,
			@RequestParam(defaultValue = "ID") SortBy sortBy, @RequestParam(required = false) String search) {

		Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(sortBy.label));
		if (descending) {
			pageable = PageRequest.of(page - 1, 5, Sort.by("idArtikla").descending());
		}

		Page<Artikl> artikli;
		if (search != null) {
			artikli = this.artiklRepository.findBynazivArtiklaContainingIgnoreCase(search, pageable);
		} else {
			artikli = this.artiklRepository.findAll(pageable);
		}

		return ObjectMapperUtils.mapPage(artikli, ArtiklDTO.class);
	}
}