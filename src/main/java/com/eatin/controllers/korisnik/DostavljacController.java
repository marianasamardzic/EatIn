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
import com.eatin.dto.korisnik.DostavljacDTO;
import com.eatin.dto.korisnik.DostavljacNoIdDTO;
import com.eatin.jpa.Dostavljac;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Uloga;
import com.eatin.repository.DostavljacRepository;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.UlogaRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class DostavljacController {

	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private DostavljacRepository dostavljacRepository;
	@Autowired
	private UlogaRepository ulogaRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@ApiOperation(value = "Izlistavanje svih dostavljaca")
	@GetMapping("admin/dostavljac")
	public Collection<DostavljacDTO> getAllDostavljac() {

		return ObjectMapperUtils.mapAll(dostavljacRepository.findAll(), DostavljacDTO.class);
	}

	@ApiOperation(value = "Registracija novog dostavljaca")
	@PostMapping("admin/register/dostavljac")
	public ResponseEntity<String> registerUser(@Valid @RequestBody DostavljacNoIdDTO dostavljacNoIdDTO)
			throws Exception {

		Korisnik korisnik = ObjectMapperUtils.map(dostavljacNoIdDTO.getKorisnik(), Korisnik.class);

		korisnik.setAktivan(true);

		Optional<Uloga> uloga = ulogaRepository.findById(3);
		korisnik.setUloga(uloga.get());

		Korisnik sacuvaniKorisnik = korisnikRepository.save(korisnik);

		jdbcTemplate.execute("insert into Dostava.Dostavljac(id_dostavljaca, prevozno_sredstvo)" + " values("
				+ sacuvaniKorisnik.getIdKorisnika() + ", '" + dostavljacNoIdDTO.getPrevoznoSredstvo() + "');");

		return new ResponseEntity<String>("Successfully added", HttpStatus.OK);

	}

	@ApiOperation(value = "Azuriranje dostavljaca")
	@PutMapping("admin/update/dostavljac/{id}")
	public ResponseEntity<String> updateDostavljac(@Validated @RequestBody DostavljacNoIdDTO dostavljacNoIdDTO,
			@PathVariable int id) {

		Korisnik korisnik = this.korisnikRepository.getOne(id);

		if (korisnik.getUloga().getIdUloge() != 3) {
			return new ResponseEntity<String>("Korisnik doesn't have dostavljac role", HttpStatus.NOT_FOUND);
		}

		else {
			Dostavljac dostavljac = this.dostavljacRepository.getOne(id);
			korisnik.setEmailKorisnika(dostavljacNoIdDTO.getKorisnik().getEmailKorisnika());
			korisnik.setLozinkaKorisnika(dostavljacNoIdDTO.getKorisnik().getLozinkaKorisnika());
			korisnik.setImeKorisnika(dostavljacNoIdDTO.getKorisnik().getImeKorisnika());
			korisnik.setPrezimeKorisnika(dostavljacNoIdDTO.getKorisnik().getPrezimeKorisnika());
			korisnik.setTelefonKorisnika(dostavljacNoIdDTO.getKorisnik().getTelefonKorisnika());

			dostavljac.setPrevoznoSredstvo(dostavljacNoIdDTO.getPrevoznoSredstvo());

			this.korisnikRepository.save(korisnik);
			this.dostavljacRepository.save(dostavljac);

			return new ResponseEntity<String>("Updated successfully", HttpStatus.OK);
		}
	}

}
