package com.eatin.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.LokacijaDTO;
import com.eatin.jpa.Klijent;
import com.eatin.jpa.Klijent_se_nalazi;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Lokacija;
import com.eatin.repository.KlijentRepository;
import com.eatin.repository.KlijentSeNalaziRepository;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.LokacijaRepository;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class LokacijaController {

	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	KlijentRepository KlijentRepository;
	@Autowired
	private KlijentSeNalaziRepository klijentSeNalaziRepository;
	@Autowired
	private LokacijaRepository lokacijaRepository;

	@ApiOperation("Prikaz svih lokacija za klijenta")
	@GetMapping("lokacija")
	public Collection<LokacijaDTO> getAllLokacijaForKlijent() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			System.out.println(username);

			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);

			System.out.print(korisnik.getIdKorisnika());

			Collection<Klijent_se_nalazi> seNalazi = this.klijentSeNalaziRepository
					.findByKlijent_idKlijenta(korisnik.getIdKorisnika());
			List<Lokacija> lokacije = new ArrayList<>();
			Iterator<Klijent_se_nalazi> iterator = seNalazi.iterator();
			while (iterator.hasNext()) {
				lokacije.add(iterator.next().getLokacija());
			}
			return ObjectMapperUtils.mapAll(lokacije, LokacijaDTO.class);
		}
		return null;
	}

	@ApiOperation("Dodavanje lokacije za klijenta")
	@PostMapping("lokacija")
	public ResponseEntity<LokacijaDTO> addLokacijaForKlijent(@Valid @RequestBody LokacijaDTO lokacijaDTO) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			System.out.println(username);

			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Klijent klijent = this.KlijentRepository.getOne(korisnik.getIdKorisnika());

			// lokacija
			Lokacija lokacija = ObjectMapperUtils.map(lokacijaDTO, Lokacija.class);
			lokacija = this.lokacijaRepository.save(lokacija);

			// klijent se nalazi
			Klijent_se_nalazi klijent_se_nalazi = new Klijent_se_nalazi();
			klijent_se_nalazi.setKlijent(klijent);
			klijent_se_nalazi.setLokacija(lokacija);
			this.klijentSeNalaziRepository.save(klijent_se_nalazi);

			return new ResponseEntity<LokacijaDTO>(ObjectMapperUtils.map(lokacija, LokacijaDTO.class),
					HttpStatus.CREATED);
		}
		return null;
	}
}
