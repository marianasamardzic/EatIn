package com.eatin.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.LokacijaDTO;
import com.eatin.jpa.Klijent_se_nalazi;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Lokacija;
import com.eatin.repository.KlijentSeNalaziRepository;
import com.eatin.repository.KorisnikRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class LokacijaController {

	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private KlijentSeNalaziRepository klijentSeNalaziRepository;

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
}
