package com.eatin.controllers;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.auth.AuthenticationRequest;
import com.eatin.dto.auth.AuthenticationResponse;
import com.eatin.dto.korisnik.DostavljacDTO;
import com.eatin.dto.korisnik.DostavljacNoIdDTO;
import com.eatin.dto.korisnik.KorisnikDTO;
import com.eatin.dto.korisnik.KorisnikNoEmailDTO;
import com.eatin.dto.korisnik.ZaposleniDTO;
import com.eatin.dto.korisnik.ZaposleniNoIdDTO;
import com.eatin.dto.porudzbina.SimplePorudzbinaDTO;
import com.eatin.enums.StatusPorudzbine;
import com.eatin.error.CustomException;
import com.eatin.jpa.Dostavljac;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Porudzbina;
import com.eatin.jpa.Restoran;
import com.eatin.jpa.Uloga;
import com.eatin.jpa.Zaposleni;
import com.eatin.repository.DostavljacRepository;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.RestoranRepository;
import com.eatin.repository.UlogaRepository;
import com.eatin.repository.ZaposleniRepository;
import com.eatin.security.JwtUtil;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AdminController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private DostavljacRepository dostavljacRepository;
	@Autowired
	private ZaposleniRepository zaposleniRepository;
	@Autowired
	private UlogaRepository ulogaRepository;
	@Autowired
	private RestoranRepository restoranRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@ApiOperation(value = "Registracija novog admina")
	@PostMapping("admin/register/admin")
	public ResponseEntity<String> registerUser(@Valid @RequestBody KorisnikDTO korisnikDTO) throws Exception {

		Korisnik korisnik = ObjectMapperUtils.map(korisnikDTO, Korisnik.class);
		
		korisnik.setAktivan(true);

		Optional<Uloga> uloga = ulogaRepository.findById(4);
		korisnik.setUloga(uloga.get());
		
		Korisnik sacuvaniKorisnik = korisnikRepository.save(korisnik);
	
		return new ResponseEntity<String>("Successfully added", HttpStatus.OK);

	}
	
	@ApiOperation(value = "Registracija novog dostavljaca")
	@PostMapping("admin/register/dostavljac")
	public ResponseEntity<String> registerUser(@Valid @RequestBody DostavljacNoIdDTO dostavljacNoIdDTO) throws Exception {

		Korisnik korisnik = ObjectMapperUtils.map(dostavljacNoIdDTO.getKorisnik(), Korisnik.class);
		
		korisnik.setAktivan(true);

		Optional<Uloga> uloga = ulogaRepository.findById(3);
		korisnik.setUloga(uloga.get());
		
		Korisnik sacuvaniKorisnik = korisnikRepository.save(korisnik);
		
		jdbcTemplate
				.execute("insert into Dostava.Dostavljac(id_dostavljaca, prevozno_sredstvo)"
						+ " values(" + sacuvaniKorisnik.getIdKorisnika() + ", '" + dostavljacNoIdDTO.getPrevoznoSredstvo() + "');");

		return new ResponseEntity<String>("Successfully added", HttpStatus.OK);

	}
	
	@ApiOperation(value = "Registracija novog zaposlenog")
	@PostMapping("admin/register/zaposleni")
	public ResponseEntity<String> registerUser(@Valid @RequestBody ZaposleniNoIdDTO zaposleniNoIdDTO) throws Exception {
		
		if(restoranRepository.findAllByidRestorana(zaposleniNoIdDTO.getRestoran()).isEmpty()) {	
			return new ResponseEntity<String>("Restoran doesn't exist", HttpStatus.NOT_FOUND);
		}
		else {

			Korisnik korisnik = ObjectMapperUtils.map(zaposleniNoIdDTO.getKorisnik(), Korisnik.class);
			
			korisnik.setAktivan(true);

			Optional<Uloga> uloga = ulogaRepository.findById(2);
			korisnik.setUloga(uloga.get());
			
			Korisnik sacuvaniKorisnik = korisnikRepository.save(korisnik);
			
			jdbcTemplate
			.execute("insert into Dostava.Zaposleni(id_zaposlenog, funkcija_zaposlenog, id_restorana)"
					+ " values(" + sacuvaniKorisnik.getIdKorisnika() + ", '" + zaposleniNoIdDTO.getFunkcijaZaposlenog()+ "', " + zaposleniNoIdDTO.getRestoran() + ");");

			return new ResponseEntity<String>("Successfully added", HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Izlistavanje svih dostavljaca")
	@GetMapping("admin/dostavljac")
	public Collection<DostavljacDTO> getAllDostavljac() {

		return ObjectMapperUtils.mapAll(dostavljacRepository.findAll(), DostavljacDTO.class);
	}
	
	@ApiOperation(value = "Izlistavanje svih zaposlenih")
	@GetMapping("admin/zaposleni")
	public Collection<ZaposleniDTO> getAllZaposleni() {

		return ObjectMapperUtils.mapAll(zaposleniRepository.findAll(), ZaposleniDTO.class);
	}
	
	@ApiOperation(value = "Azuriranje admina")
	@PutMapping("admin/update/admin/{id}")
	public ResponseEntity<String> updateAdmin(@Validated @RequestBody KorisnikDTO korisnikDTO, @PathVariable int id) {

		Korisnik korisnik = this.korisnikRepository.getOne(id);

		if(korisnik.getUloga().getIdUloge() != 4)
		{
			return new ResponseEntity<String>("Korisnik doesn't have admin role", HttpStatus.NOT_FOUND);
		}
	
		else
		{
			korisnik.setEmailKorisnika(korisnikDTO.getEmailKorisnika());
			korisnik.setLozinkaKorisnika(korisnikDTO.getLozinkaKorisnika());
			korisnik.setImeKorisnika(korisnikDTO.getImeKorisnika());
			korisnik.setPrezimeKorisnika(korisnikDTO.getPrezimeKorisnika());
			korisnik.setTelefonKorisnika(korisnikDTO.getTelefonKorisnika());
	
			this.korisnikRepository.save(korisnik);
			KorisnikDTO dto = ObjectMapperUtils.map(korisnik, KorisnikDTO.class);
			return new ResponseEntity<String>("Updated successfully", HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Azuriranje klijenta")
	@PutMapping("admin/update/klijent/{id}")
	public ResponseEntity<String> updateKlijent(@Validated @RequestBody KorisnikDTO korisnikDTO, @PathVariable int id) {

		Korisnik korisnik = this.korisnikRepository.getOne(id);

		if(korisnik.getUloga().getIdUloge() != 1)
		{
			return new ResponseEntity<String>("Korisnik doesn't have klijent role", HttpStatus.NOT_FOUND);
		}
	
		else
		{
			korisnik.setEmailKorisnika(korisnikDTO.getEmailKorisnika());
			korisnik.setLozinkaKorisnika(korisnikDTO.getLozinkaKorisnika());
			korisnik.setImeKorisnika(korisnikDTO.getImeKorisnika());
			korisnik.setPrezimeKorisnika(korisnikDTO.getPrezimeKorisnika());
			korisnik.setTelefonKorisnika(korisnikDTO.getTelefonKorisnika());
	
			this.korisnikRepository.save(korisnik);
			KorisnikDTO dto = ObjectMapperUtils.map(korisnik, KorisnikDTO.class);
			return new ResponseEntity<String>("Updated successfully", HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Azuriranje dostavljaca")
	@PutMapping("admin/update/dostavljac/{id}")
	public ResponseEntity<String> updateDostavljac(@Validated @RequestBody DostavljacNoIdDTO dostavljacNoIdDTO, @PathVariable int id) {

		Korisnik korisnik = this.korisnikRepository.getOne(id);

		if(korisnik.getUloga().getIdUloge() != 3)
		{
			return new ResponseEntity<String>("Korisnik doesn't have dostavljac role", HttpStatus.NOT_FOUND);
		}
	
		else
		{
			Dostavljac dostavljac = this.dostavljacRepository.getOne(id);
			korisnik.setEmailKorisnika(dostavljacNoIdDTO.getKorisnik().getEmailKorisnika());
			korisnik.setLozinkaKorisnika(dostavljacNoIdDTO.getKorisnik().getLozinkaKorisnika());
			korisnik.setImeKorisnika(dostavljacNoIdDTO.getKorisnik().getImeKorisnika());
			korisnik.setPrezimeKorisnika(dostavljacNoIdDTO.getKorisnik().getPrezimeKorisnika());
			korisnik.setTelefonKorisnika(dostavljacNoIdDTO.getKorisnik().getTelefonKorisnika());

			dostavljac.setPrevoznoSredstvo(dostavljacNoIdDTO.getPrevoznoSredstvo());
	
			this.korisnikRepository.save(korisnik);
			this.dostavljacRepository.save(dostavljac);
			
			KorisnikDTO dto = ObjectMapperUtils.map(korisnik, KorisnikDTO.class);
			DostavljacDTO ddto = ObjectMapperUtils.map(dostavljac, DostavljacDTO.class);

			return new ResponseEntity<String>("Updated successfully", HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Azuriranje zaposlenog")
	@PutMapping("admin/update/zaposleni/{id}")
	public ResponseEntity<String> updateZaposleni(@Validated @RequestBody ZaposleniNoIdDTO zaposleniNoIdDTO, @PathVariable int id) {

		Korisnik korisnik = this.korisnikRepository.getOne(id);

		if(korisnik.getUloga().getIdUloge() != 2)
		{
			return new ResponseEntity<String>("Korisnik doesn't have zaposleni role", HttpStatus.NOT_FOUND);
		}
	
		else
		{
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
			
			KorisnikDTO dto = ObjectMapperUtils.map(korisnik, KorisnikDTO.class);
			ZaposleniDTO ddto = ObjectMapperUtils.map(zaposleni, ZaposleniDTO.class);

			return new ResponseEntity<String>("Updated successfully", HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Brisanje korisnika - postavljanje aktuelno na 0")
	@DeleteMapping("admin/delete/korisnici/{id}")
	public ResponseEntity<String> deleteKorisnik(@PathVariable int id) {
		
		Korisnik korisnik = this.korisnikRepository.getOne(id);
		if(korisnik.getAktivan() == false)
		{
			return new ResponseEntity<String>("Korisnik is already deleted", HttpStatus.NOT_FOUND);			
		}
		else 
		{
			korisnik.setAktivan(false);
			this.korisnikRepository.save(korisnik);
			return new ResponseEntity<String>("Deleted successfully", HttpStatus.OK);
		}
	}
}
