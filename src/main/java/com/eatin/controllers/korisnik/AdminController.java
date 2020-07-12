package com.eatin.controllers.korisnik;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.korisnik.KorisnikDTO;
import com.eatin.dto.korisnik.KorisnikWithIdDTO;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Uloga;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.UlogaRepository;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AdminController {

	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private UlogaRepository ulogaRepository;
	
	@ApiOperation(value = "Registracija novog admina")
	@PostMapping("admin/register/admin")
	public ResponseEntity<String> registerUser(@Valid @RequestBody KorisnikDTO korisnikDTO) throws Exception {

		Korisnik korisnik = ObjectMapperUtils.map(korisnikDTO, Korisnik.class);
		
		korisnik.setAktivan(true);

		Optional<Uloga> uloga = ulogaRepository.findById(4);
		korisnik.setUloga(uloga.get());
		
		korisnikRepository.save(korisnik);
	
		return new ResponseEntity<String>("Successfully added", HttpStatus.OK);

	}
	
	@ApiOperation(value = "Izlistavanje svih admina")
	@GetMapping("admin/admin")
	public Collection<KorisnikWithIdDTO> getAllAdmin() {

		return ObjectMapperUtils.mapAll(korisnikRepository.findByUloga_idUloge(4), KorisnikWithIdDTO.class);
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
