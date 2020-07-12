package com.eatin.controllers.korisnik;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.korisnik.KorisnikDTO;
import com.eatin.dto.korisnik.KorisnikWithIdDTO;
import com.eatin.jpa.Korisnik;
import com.eatin.repository.KorisnikRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class KlijentController {

	@Autowired
	private KorisnikRepository korisnikRepository;

	@ApiOperation(value = "Izlistavanje svih klijenata")
	@GetMapping("admin/klijent")
	public Collection<KorisnikWithIdDTO> getAllKlijent() {

		return ObjectMapperUtils.mapAll(korisnikRepository.findByUloga_idUloge(1), KorisnikWithIdDTO.class);
	}

	@ApiOperation(value = "Azuriranje klijenta")
	@PutMapping("admin/update/klijent/{id}")
	public ResponseEntity<String> updateKlijent(@Validated @RequestBody KorisnikDTO korisnikDTO, @PathVariable int id) {

		Korisnik korisnik = this.korisnikRepository.getOne(id);

		if (korisnik.getUloga().getIdUloge() != 1) {
			return new ResponseEntity<String>("Korisnik doesn't have klijent role", HttpStatus.NOT_FOUND);
		}

		else {
			korisnik.setEmailKorisnika(korisnikDTO.getEmailKorisnika());
			korisnik.setLozinkaKorisnika(korisnikDTO.getLozinkaKorisnika());
			korisnik.setImeKorisnika(korisnikDTO.getImeKorisnika());
			korisnik.setPrezimeKorisnika(korisnikDTO.getPrezimeKorisnika());
			korisnik.setTelefonKorisnika(korisnikDTO.getTelefonKorisnika());

			this.korisnikRepository.save(korisnik);
			return new ResponseEntity<String>("Updated successfully", HttpStatus.OK);
		}
	}

}
