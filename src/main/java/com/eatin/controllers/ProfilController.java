package com.eatin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.korisnik.KorisnikDTO;
import com.eatin.dto.korisnik.KorisnikNoEmailDTO;
import com.eatin.jpa.Korisnik;
import com.eatin.repository.KorisnikRepository;

@RestController
public class ProfilController {

	@Autowired
	private KorisnikRepository korisnikRepository;

	@GetMapping("/profil")
	public ResponseEntity<KorisnikDTO> getProfil() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			KorisnikDTO korisnikDTO = ObjectMapperUtils.map(korisnik, KorisnikDTO.class);
			return new ResponseEntity<KorisnikDTO>(korisnikDTO, HttpStatus.OK);

		}
		return null;
	}

	@PutMapping("/profil")
	public ResponseEntity<KorisnikDTO> updateProfil(@Validated @RequestBody KorisnikNoEmailDTO korisnikDTO) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);

			korisnik.setLozinkaKorisnika(korisnikDTO.getLozinkaKorisnika());
			korisnik.setImeKorisnika(korisnikDTO.getImeKorisnika());
			korisnik.setPrezimeKorisnika(korisnikDTO.getPrezimeKorisnika());
			korisnik.setTelefonKorisnika(korisnikDTO.getTelefonKorisnika());

			this.korisnikRepository.save(korisnik);
			KorisnikDTO dto = ObjectMapperUtils.map(korisnik, KorisnikDTO.class);
			return new ResponseEntity<KorisnikDTO>(dto, HttpStatus.OK);
		}
		return null;
	}

}
