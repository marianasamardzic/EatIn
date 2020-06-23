package com.eatin.controllers;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.porudzbina.PorudzbinaDTO;
import com.eatin.enums.StatusPorudzbine;
import com.eatin.jpa.Dostavljac;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Porudzbina;
import com.eatin.repository.DostavljacRepository;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.PorudzbinaRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class DostavljacPorudzbina {

	@Autowired
	private PorudzbinaRepository porudzbinaRepository;
	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private DostavljacRepository dostavljacPorudzbina;

	@ApiOperation(value = "Prikaz svih porudzbina koje pripadaju dostavljacu")
	@GetMapping("dostavljac-porudzbina")
	public ResponseEntity<Page<PorudzbinaDTO>> getAllPorudzbinaForDostavljac(
			@RequestParam(defaultValue = "1") @Min(1) int page,
			@RequestParam(required = false) StatusPorudzbine statusPorudzbine) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			// dobavljac
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);

			// porudzbine
			Pageable pageable = PageRequest.of(page - 1, 5);
			Page<PorudzbinaDTO> porudzbineDTO;
			Page<Porudzbina> entiteti;
			if (statusPorudzbine != null) {
				entiteti = this.porudzbinaRepository.findByDostavljac_idDostavljacaAndStatusPorudzbineIgnoreCase(
						korisnik.getIdKorisnika(), statusPorudzbine.label, pageable);
			} else {
				entiteti = this.porudzbinaRepository.findByDostavljac_idDostavljaca(korisnik.getIdKorisnika(),
						pageable);
			}
			porudzbineDTO = ObjectMapperUtils.mapPage(entiteti, PorudzbinaDTO.class);

			return new ResponseEntity<Page<PorudzbinaDTO>>(porudzbineDTO, HttpStatus.OK);
		}
		return null;
	}

	@ApiOperation(value = "Prikaz svih gotovih porudzbina koje dostavljac moze da prihvati")
	@GetMapping("dostavljac-porudzbina-gotova")
	public ResponseEntity<Page<PorudzbinaDTO>> getAllGotovaPorudzbina(
			@RequestParam(defaultValue = "1") @Min(1) int page) {

		Pageable pageable = PageRequest.of(page - 1, 5);
		Page<PorudzbinaDTO> porudzbineDTO;
		Page<Porudzbina> entiteti = this.porudzbinaRepository
				.findByStatusPorudzbineIgnoreCase(StatusPorudzbine.GOTOVA.label, pageable);
		porudzbineDTO = ObjectMapperUtils.mapPage(entiteti, PorudzbinaDTO.class);
		return new ResponseEntity<Page<PorudzbinaDTO>>(porudzbineDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Izmena statusa porudzbine iz prihvacena u isporucena")
	@PutMapping("dostavljac-porudzbina-isporucena/{id}")
	public ResponseEntity<PorudzbinaDTO> setPorudzbinaIsporucena(@PathVariable int id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			// dostavljac
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Dostavljac dostavljac = this.dostavljacPorudzbina.getOne(korisnik.getIdKorisnika());

			// provera
			Porudzbina porudzbina = this.porudzbinaRepository.getOne(id);
			if (porudzbina.getStatusPorudzbine() != StatusPorudzbine.PRIHVACENA.label) {
				throw new BadCredentialsException("Porudzbina nije prihvacena");
			}
			if (porudzbina.getDostavljac().getIdDostavljaca() != dostavljac.getIdDostavljaca()) {
				throw new BadCredentialsException("Porudzbina ne pripada dostavljacu");
			}

			// izmena
			porudzbina.setStatusPorudzbine(StatusPorudzbine.ISPORUCENA.label);
			this.porudzbinaRepository.save(porudzbina);
			PorudzbinaDTO porudzbinaDTO = ObjectMapperUtils.map(porudzbina, PorudzbinaDTO.class);
			return new ResponseEntity<PorudzbinaDTO>(porudzbinaDTO, HttpStatus.OK);
		}
		return null;

	}

	@ApiOperation(value = "Izmena statusa porudzbine iz gotova u prihvacena")
	@PutMapping("dostavljac-porudzbina-prihvacena/{id}")
	public ResponseEntity<PorudzbinaDTO> setPorudzbinaPrihvacena(@PathVariable int id) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			// dobavljac
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Dostavljac dostavljac = this.dostavljacPorudzbina.getOne(korisnik.getIdKorisnika());

			// provera
			Porudzbina porudzbina = this.porudzbinaRepository.getOne(id);
			if (porudzbina.getStatusPorudzbine() != StatusPorudzbine.GOTOVA.label) {
				throw new BadCredentialsException("Porudzbina nije gotova");
			}

			// izmena
			porudzbina.setStatusPorudzbine(StatusPorudzbine.PRIHVACENA.label);
			porudzbina.setDostavljac(dostavljac);
			this.porudzbinaRepository.save(porudzbina);
			PorudzbinaDTO porudzbinaDTO = ObjectMapperUtils.map(porudzbina, PorudzbinaDTO.class);
			return new ResponseEntity<PorudzbinaDTO>(porudzbinaDTO, HttpStatus.OK);

		}
		return null;

	}
}
