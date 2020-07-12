package com.eatin.controllers.artikl;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.PrilogDTO;
import com.eatin.dto.artikl.ArtiklDTO;
import com.eatin.dto.artikl.ArtiklNoIdDTO;
import com.eatin.jpa.Artikl;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Restoran;
import com.eatin.jpa.Zaposleni;
import com.eatin.repository.ArtiklRepository;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.TipArtiklaRepository;
import com.eatin.repository.ZaposleniRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class ArtiklZaposleniController {

	@Autowired
	private ArtiklRepository artiklRepository;
	@Autowired
	private TipArtiklaRepository tipArtiklaRepository;
	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private ZaposleniRepository zaposleniRepository;

	@ApiOperation("Kreira novi artikl")
	@PostMapping("artikl-zaposleni")
	public ResponseEntity<ArtiklDTO> createArtikl(@RequestBody ArtiklNoIdDTO artikl) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			// izvlacenje zaposlenog
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());
			Restoran restoran = zaposleni.getRestoran();

			Artikl entity = ObjectMapperUtils.map(artikl, Artikl.class);
			entity.setRestoran(restoran);

			Iterator<PrilogDTO> prilogIterator = artikl.getPrilozi().iterator();
			while (prilogIterator.hasNext()) {
				PrilogDTO prilog = prilogIterator.next();

			}

		}

		return null;
	}

}
