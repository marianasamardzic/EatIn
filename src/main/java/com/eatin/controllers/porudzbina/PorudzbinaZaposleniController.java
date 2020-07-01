package com.eatin.controllers.porudzbina;

import javax.persistence.EntityNotFoundException;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.porudzbina.PorudzbinaDTO;
import com.eatin.enums.StatusPorudzbine;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Porudzbina;
import com.eatin.jpa.Zaposleni;
import com.eatin.jpa.ZaposleniRepository;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.PorudzbinaRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class PorudzbinaZaposleniController {

	@Autowired
	private ZaposleniRepository zaposleniRepository;
	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private PorudzbinaRepository porudzbinaRepository;

	@ApiOperation("Izlistava sve primljene porudzbine za restoran u kom zaposleni radi")
	@GetMapping("zaposleni-porudzbina")
	public ResponseEntity<Page<PorudzbinaDTO>> getAllPorudzbinaZaposleni(
			@RequestParam(defaultValue = "1") @Min(1) int page,
			@RequestParam(required = false) StatusPorudzbine statusPorudzbine) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());

			Pageable pageable = PageRequest.of(page - 1, 5);
			Page<Porudzbina> entiteti;
			if (statusPorudzbine != null) {
				entiteti = this.porudzbinaRepository.findByRestoran_idRestoranaAndStatusPorudzbineIgnoreCase(
						zaposleni.getRestoran().getIdRestorana(), statusPorudzbine.label, pageable);
			} else {
				entiteti = this.porudzbinaRepository
						.findByRestoran_idRestorana(zaposleni.getRestoran().getIdRestorana(), pageable);
			}
			Page<PorudzbinaDTO> porudzbineDTO = ObjectMapperUtils.mapPage(entiteti, PorudzbinaDTO.class);

			return new ResponseEntity<Page<PorudzbinaDTO>>(porudzbineDTO, HttpStatus.OK);
		}
		return null;
	}

	@ApiOperation(value = "Izmena statusa porudzbine iz primljena u gotova")
	@PutMapping("zaposleni-porudzbina-gotova/{id}")
	@CrossOrigin
	public ResponseEntity<PorudzbinaDTO> setPorudzbinaGotova(@PathVariable int id) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			// zaposleni
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());

			// provera da li porudzbina postoji
			if (!this.porudzbinaRepository.existsById(id)) {
				throw new EntityNotFoundException("Could not find porudzbina with id " + id);
			}
			Porudzbina porudzbina = this.porudzbinaRepository.getOne(id);

			// provera da li je porudzbina primljena
			if (porudzbina.getStatusPorudzbine() != StatusPorudzbine.PRIMLJENA.label) {
				throw new BadCredentialsException("Porudzbina nije primljena");
			}

			// provera da li porudzbina pripada tom restoranu
			if (porudzbina.getRestoran().getIdRestorana() != zaposleni.getRestoran().getIdRestorana()) {
				throw new BadCredentialsException("Porudzbina ne pripada restoranu u kom radi zaposleni");
			}

			// izmena
			porudzbina.setStatusPorudzbine(StatusPorudzbine.GOTOVA.label);
			this.porudzbinaRepository.save(porudzbina);
			PorudzbinaDTO porudzbinaDTO = ObjectMapperUtils.map(porudzbina, PorudzbinaDTO.class);
			return new ResponseEntity<PorudzbinaDTO>(porudzbinaDTO, HttpStatus.OK);

		}
		return null;

	}
}
