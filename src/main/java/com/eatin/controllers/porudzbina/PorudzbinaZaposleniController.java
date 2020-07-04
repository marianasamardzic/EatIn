package com.eatin.controllers.porudzbina;

import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.porudzbina.SimplePorudzbinaDTO;
import com.eatin.enums.StatusPorudzbine;
import com.eatin.error.CustomException;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Porudzbina;
import com.eatin.jpa.Zaposleni;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.PorudzbinaRepository;
import com.eatin.repository.ZaposleniRepository;

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
	public ResponseEntity<Page<SimplePorudzbinaDTO>> getAllPorudzbinaZaposleni(
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
			Page<SimplePorudzbinaDTO> porudzbineDTO = ObjectMapperUtils.mapPage(entiteti, SimplePorudzbinaDTO.class);

			return new ResponseEntity<Page<SimplePorudzbinaDTO>>(porudzbineDTO, HttpStatus.OK);
		}
		return null;
	}

	@ApiOperation(value = "Izmena statusa porudzbine iz primljena u gotova")
	@PutMapping("zaposleni-porudzbina-gotova/{id}")
	public ResponseEntity<SimplePorudzbinaDTO> setPorudzbinaGotova(@PathVariable int id) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			// zaposleni
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());

			Optional<Porudzbina> porudzbina = this.porudzbinaRepository.findById(id);

			// provera da li postoji data porudzbina
			if (porudzbina.isEmpty()) {
				throw new CustomException("Ne postoji porudzbina sa datim id-jem");
			}

			// provera da li porudzbina pripada tom restoranu
			if (porudzbina.get().getRestoran().getIdRestorana() != zaposleni.getRestoran().getIdRestorana()) {
				throw new CustomException("Porudzbina ne pripada restoranu");
			}

			// provera da li je porudzbina primljena
			if (!porudzbina.get().getStatusPorudzbine().equals(StatusPorudzbine.PRIMLJENA.label)) {
				throw new CustomException("Porudzbina nije primljena");
			}

			// izmena
			porudzbina.get().setStatusPorudzbine(StatusPorudzbine.GOTOVA.label);
			this.porudzbinaRepository.save(porudzbina.get());
			SimplePorudzbinaDTO porudzbinaDTO = ObjectMapperUtils.map(porudzbina.get(), SimplePorudzbinaDTO.class);
			return new ResponseEntity<SimplePorudzbinaDTO>(porudzbinaDTO, HttpStatus.OK);

		}
		return null;

	}
}
