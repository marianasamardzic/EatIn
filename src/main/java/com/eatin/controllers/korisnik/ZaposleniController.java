package com.eatin.controllers.korisnik;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.korisnik.ZaposleniDTO;
import com.eatin.dto.korisnik.ZaposleniNoIdDTO;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Restoran;
import com.eatin.jpa.Uloga;
import com.eatin.jpa.Zaposleni;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.RestoranRepository;
import com.eatin.repository.UlogaRepository;
import com.eatin.repository.ZaposleniRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class ZaposleniController {

	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private ZaposleniRepository zaposleniRepository;
	@Autowired
	private UlogaRepository ulogaRepository;
	@Autowired
	private RestoranRepository restoranRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@ApiOperation(value = "Izlistavanje svih zaposlenih")
	@GetMapping("admin/zaposleni")
	public Collection<ZaposleniDTO> getAllZaposleni() {

		return ObjectMapperUtils.mapAll(zaposleniRepository.findAll(), ZaposleniDTO.class);
	}

	@ApiOperation(value = "Registracija novog zaposlenog")
	@PostMapping("admin/register/zaposleni")
	public ResponseEntity<String> registerUser(@Valid @RequestBody ZaposleniNoIdDTO zaposleniNoIdDTO) throws Exception {

		if (restoranRepository.findAllByidRestorana(zaposleniNoIdDTO.getRestoranId()).isEmpty()) {
			return new ResponseEntity<String>("Restoran doesn't exist", HttpStatus.NOT_FOUND);
		} else {

			Korisnik korisnik = ObjectMapperUtils.map(zaposleniNoIdDTO.getKorisnik(), Korisnik.class);

			korisnik.setAktivan(true);

			Optional<Uloga> uloga = ulogaRepository.findById(2);
			korisnik.setUloga(uloga.get());

			Korisnik sacuvaniKorisnik = korisnikRepository.save(korisnik);

			jdbcTemplate.execute("insert into Dostava.Zaposleni(id_zaposlenog, funkcija_zaposlenog, id_restorana)"
					+ " values(" + sacuvaniKorisnik.getIdKorisnika() + ", '" + zaposleniNoIdDTO.getFunkcijaZaposlenog()
					+ "', " + zaposleniNoIdDTO.getRestoranId() + ");");

			return new ResponseEntity<String>("Successfully added", HttpStatus.OK);
		}
	}

	@ApiOperation(value = "Azuriranje zaposlenog")
	@PutMapping("admin/update/zaposleni/{id}")
	public ResponseEntity<String> updateZaposleni(@Validated @RequestBody ZaposleniNoIdDTO zaposleniNoIdDTO,
			@PathVariable int id) {

		Korisnik korisnik = this.korisnikRepository.getOne(id);

		if (korisnik.getUloga().getIdUloge() != 2) {
			return new ResponseEntity<String>("Korisnik doesn't have zaposleni role", HttpStatus.NOT_FOUND);
		}

		else {
			Zaposleni zaposleni = this.zaposleniRepository.getOne(id);
			Restoran restoran = this.restoranRepository.getOne(zaposleniNoIdDTO.getRestoranId());

			korisnik.setEmailKorisnika(zaposleniNoIdDTO.getKorisnik().getEmailKorisnika());
			korisnik.setLozinkaKorisnika(zaposleniNoIdDTO.getKorisnik().getLozinkaKorisnika());
			korisnik.setImeKorisnika(zaposleniNoIdDTO.getKorisnik().getImeKorisnika());
			korisnik.setPrezimeKorisnika(zaposleniNoIdDTO.getKorisnik().getPrezimeKorisnika());
			korisnik.setTelefonKorisnika(zaposleniNoIdDTO.getKorisnik().getTelefonKorisnika());

			zaposleni.setFunkcijaZaposlenog(zaposleniNoIdDTO.getFunkcijaZaposlenog());
			zaposleni.setRestoran(restoran);

			this.korisnikRepository.save(korisnik);
			this.zaposleniRepository.save(zaposleni);

			return new ResponseEntity<String>("Updated successfully", HttpStatus.OK);
		}
	}

}
