package com.eatin.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.porudzbina.CreatePorudzbinaDTO;
import com.eatin.dto.porudzbina.PorudzbinaDTO;
import com.eatin.enums.StatusPorudzbine;
import com.eatin.jpa.Ima_priloge;
import com.eatin.jpa.Klijent;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Porudzbina;
import com.eatin.jpa.StavkaPorudzbine;
import com.eatin.repository.ImaPrilogeRepository;
import com.eatin.repository.KlijentRepository;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.PorudzbinaRepository;
import com.eatin.repository.StavkaPorudzbineRepository;

@RestController
public class KlijentPorudzbinaController {
	@Autowired
	private PorudzbinaRepository porudzbinaRepository;
	@Autowired
	private ImaPrilogeRepository imaPrilogeRepository;
	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private KlijentRepository klijentRepository;
	@Autowired
	private StavkaPorudzbineRepository stavkaPorudzbineRepository;

	@PostMapping("klijent-porudzbina")
	public ResponseEntity<PorudzbinaDTO> createPorudzbina(@RequestBody CreatePorudzbinaDTO createPorudzbinaDTO) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			Porudzbina porudzbina = ObjectMapperUtils.map(createPorudzbinaDTO, Porudzbina.class);

			// setovanje klijenta
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Klijent klijent = this.klijentRepository.getOne(korisnik.getIdKorisnika());
			porudzbina.setKlijent(klijent);

			// setovanje statusa
			porudzbina.setStatusPorudzbine(StatusPorudzbine.PRIMLJENA.label);

			// setovanje datuma
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date());
			System.out.println(date);
			porudzbina.setVremePrijemaPorudzbine(date);
			porudzbina.setVremeIsporukePorudzbine(null);

			// cuvanje porudzbine
			System.out.println("Cuvanje porudzbine");
			Porudzbina savedPorudzbina = this.porudzbinaRepository.save(porudzbina);
			int id = savedPorudzbina.getIdPorudzbine();

			// cuvanje stavki porudzbine
			List<StavkaPorudzbine> stavkePorudzbine = porudzbina.getStavkePorudzbine();
			Iterator<StavkaPorudzbine> iterator = stavkePorudzbine.iterator();
			while (iterator.hasNext()) {

				StavkaPorudzbine sp = iterator.next();
				Porudzbina p = new Porudzbina();
				p.setIdPorudzbine(id);
				sp.setPorudzbina(p);
				System.out.println("Cuvanje stavki porudzbine");
				StavkaPorudzbine savedStavkaPorudzbine = this.stavkaPorudzbineRepository.save(sp);

				// cuavnje ima priloga
				Iterator<Ima_priloge> iteratorImaPriloge = sp.getImaPriloge().iterator();
				while (iteratorImaPriloge.hasNext()) {
					Ima_priloge im = iteratorImaPriloge.next();
					im.setStavkaPorudzbine(savedStavkaPorudzbine);
					System.out.println("Cuvanje ima priloge");
					this.imaPrilogeRepository.save(im);
				}

			}

			System.out.println("Izvlacenje iz baze");
			PorudzbinaDTO responsePorudzbinaDTO = ObjectMapperUtils.map(this.porudzbinaRepository.getOne(id),
					PorudzbinaDTO.class);

			return new ResponseEntity<PorudzbinaDTO>(responsePorudzbinaDTO, HttpStatus.OK);
		}
		return null;
	}

	@GetMapping("klijent-porudzbina")
	public ResponseEntity<Page<PorudzbinaDTO>> getAllPorudzbinaKlijent(
			@RequestParam(defaultValue = "1") @Min(1) int page,
			@RequestParam(required = false) StatusPorudzbine statusPorudzbine) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);

			Pageable pageable = PageRequest.of(page - 1, 5);
			Page<PorudzbinaDTO> porudzbineDTO;
			Page<Porudzbina> entiteti;
			if (statusPorudzbine != null) {
				entiteti = this.porudzbinaRepository.findByKlijent_idKlijentaAndStatusPorudzbineIgnoreCase(
						korisnik.getIdKorisnika(), statusPorudzbine.label, pageable);
			} else {
				entiteti = this.porudzbinaRepository.findByKlijent_idKlijenta(korisnik.getIdKorisnika(), pageable);
			}
			porudzbineDTO = ObjectMapperUtils.mapPage(entiteti, PorudzbinaDTO.class);

			return new ResponseEntity<Page<PorudzbinaDTO>>(porudzbineDTO, HttpStatus.OK);
		}
		return null;
	}
}