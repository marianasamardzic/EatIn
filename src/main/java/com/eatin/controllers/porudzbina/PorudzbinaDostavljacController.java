package com.eatin.controllers.porudzbina;

import java.util.Optional;

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
import com.eatin.dto.porudzbina.SimplePorudzbinaDTO;
import com.eatin.enums.StatusPorudzbine;
import com.eatin.error.CustomException;
import com.eatin.jpa.Dostavljac;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Porudzbina;
import com.eatin.repository.DostavljacRepository;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.PorudzbinaRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class PorudzbinaDostavljacController {

	@Autowired
	private PorudzbinaRepository porudzbinaRepository;
	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private DostavljacRepository dostavljacRepository;

	@ApiOperation(value = "Prikaz svih gotovih porudzbina koje dostavljac moze da prihvati")
	@GetMapping("dostavljac-porudzbina-gotova")
	public ResponseEntity<Page<SimplePorudzbinaDTO>> getAllGotovaPorudzbina(
			@RequestParam(defaultValue = "1") @Min(1) int page) {

		Pageable pageable = PageRequest.of(page - 1, 5);
		Page<Porudzbina> entiteti = this.porudzbinaRepository
				.findByStatusPorudzbineIgnoreCase(StatusPorudzbine.GOTOVA.label, pageable);
		Page<SimplePorudzbinaDTO> porudzbineDTO = ObjectMapperUtils.mapPage(entiteti, SimplePorudzbinaDTO.class);
		return new ResponseEntity<Page<SimplePorudzbinaDTO>>(porudzbineDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Izmena statusa porudzbine iz gotova u prihvacena")
	@PutMapping("dostavljac-porudzbina-prihvacena/{id}")
	public ResponseEntity<SimplePorudzbinaDTO> setPorudzbinaPrihvacena(@PathVariable int id) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			// dobavljac
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Dostavljac dostavljac = this.dostavljacRepository.getOne(korisnik.getIdKorisnika());

			Optional<Porudzbina> porudzbina = this.porudzbinaRepository.findById(id);

			// provera da li postoji data porudzbina
			if (!porudzbina.isPresent()) {
				throw new CustomException("Ne postoji porudzbina sa datim id-jem");
			}

			// provera da li je porudzbina gotova
			if (!porudzbina.get().getStatusPorudzbine().equals(StatusPorudzbine.GOTOVA.label)) {
				throw new CustomException("Porudzbina nije gotova");
			}

			// izmena
			porudzbina.get().setStatusPorudzbine(StatusPorudzbine.PRIHVACENA.label);
			porudzbina.get().setDostavljac(dostavljac);
			this.porudzbinaRepository.save(porudzbina.get());
			SimplePorudzbinaDTO porudzbinaDTO = ObjectMapperUtils.map(porudzbina.get(), SimplePorudzbinaDTO.class);
			return new ResponseEntity<SimplePorudzbinaDTO>(porudzbinaDTO, HttpStatus.OK);

		}
		return null;

	}

	@ApiOperation(value = "Prikaz svih porudzbina koje pripadaju dostavljacu")
	@GetMapping("dostavljac-porudzbina")
	public ResponseEntity<Page<SimplePorudzbinaDTO>> getAllPorudzbinaForDostavljac(
			@RequestParam(defaultValue = "1") @Min(1) int page,
			@RequestParam(required = false) StatusPorudzbine statusPorudzbine) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			// dobavljac
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);

			// porudzbine
			Pageable pageable = PageRequest.of(page - 1, 5);
			Page<Porudzbina> entiteti;
			if (statusPorudzbine != null) {
				entiteti = this.porudzbinaRepository.findByDostavljac_idDostavljacaAndStatusPorudzbineIgnoreCase(
						korisnik.getIdKorisnika(), statusPorudzbine.label, pageable);
			} else {
				entiteti = this.porudzbinaRepository.findByDostavljac_idDostavljaca(korisnik.getIdKorisnika(),
						pageable);
			}
			Page<SimplePorudzbinaDTO> porudzbineDTO = ObjectMapperUtils.mapPage(entiteti, SimplePorudzbinaDTO.class);

			return new ResponseEntity<Page<SimplePorudzbinaDTO>>(porudzbineDTO, HttpStatus.OK);
		}
		return null;
	}


	@ApiOperation(value = "Izmena statusa porudzbine iz prihvacena u isporucena")
	@PutMapping("dostavljac-porudzbina-isporucena/{id}")
	public ResponseEntity<SimplePorudzbinaDTO> setPorudzbinaIsporucena(@PathVariable int id) throws Exception {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			// dostavljac
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Dostavljac dostavljac = this.dostavljacRepository.getOne(korisnik.getIdKorisnika());

			Optional<Porudzbina> porudzbina = this.porudzbinaRepository.findById(id);

			// provera da li postoji data porudzbina
			if (!porudzbina.isPresent()) {
				throw new CustomException("Ne postoji porudzbina sa datim id-jem");
			}

			// provera da li porudzbina pripada dobavljacu
			if (porudzbina.get().getDostavljac().getIdDostavljaca() != dostavljac.getIdDostavljaca()) {
				throw new BadCredentialsException("Porudzbina ne pripada dostavljacu");
			}

			// provera da li je porudzbina prihvacena
			if (!porudzbina.get().getStatusPorudzbine().equals(StatusPorudzbine.PRIHVACENA.label)) {
				throw new CustomException("Porudzbina nije porudzbina");
			}

			// izmena
			porudzbina.get().setStatusPorudzbine(StatusPorudzbine.ISPORUCENA.label);
			this.porudzbinaRepository.save(porudzbina.get());
			SimplePorudzbinaDTO porudzbinaDTO = ObjectMapperUtils.map(porudzbina, SimplePorudzbinaDTO.class);
			return new ResponseEntity<SimplePorudzbinaDTO>(porudzbinaDTO, HttpStatus.OK);
		}
		return null;

	}


}
