package com.eatin.controllers.restoran;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.LokacijaDTO;
import com.eatin.dto.LokacijaNoIdDTO;
import com.eatin.dto.restoran.PutRestoranDTO;
import com.eatin.dto.restoran.RadnoVremeDTO;
import com.eatin.dto.restoran.RestoranDTO;
import com.eatin.dto.restoran.TipRestoranaDTO;
import com.eatin.error.CustomException;
import com.eatin.jpa.Je_tipa;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Lokacija;
import com.eatin.jpa.RadnoVreme;
import com.eatin.jpa.Restoran;
import com.eatin.jpa.Restoran_se_nalazi;
import com.eatin.jpa.Tip_datuma;
import com.eatin.jpa.Tip_restorana;
import com.eatin.jpa.Zaposleni;
import com.eatin.repository.JeTipaRepository;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.LokacijaRepository;
import com.eatin.repository.RadnoVremeRepository;
import com.eatin.repository.RestoranRepository;
import com.eatin.repository.RestoranSeNalaziRepository;
import com.eatin.repository.TipDatumaRepository;
import com.eatin.repository.TipRestoranaRepository;
import com.eatin.repository.ZaposleniRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class RestoranZaposleniController {

	@Autowired
	private RestoranRepository restoranRepository;
	@Autowired
	private RestoranSeNalaziRepository restoranSeNalaziRepository;
	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private ZaposleniRepository zaposleniRepository;
	@Autowired
	private LokacijaRepository lokacijaRepository;
	@Autowired
	private JeTipaRepository jeTipaRepository;
	@Autowired
	private TipRestoranaRepository tipRestoranaRepository;
	@Autowired
	private RadnoVremeRepository radnoVremeRepository;
	@Autowired
	private TipDatumaRepository tipDatumaRepository;

	@ApiOperation("Vraca restoran u kom radi zaposleni")
	@GetMapping("restoran-zaposleni")
	public ResponseEntity<RestoranDTO> getRestoranForZaposleni() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			// izvlacenje zaposlenog
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());

			// mapiranje
			RestoranDTO restoranDTO = ObjectMapperUtils.map(zaposleni.getRestoran(), RestoranDTO.class);

			// lokacije
			Collection<Restoran_se_nalazi> nalazi = this.restoranSeNalaziRepository
					.findByRestoran_idRestorana(zaposleni.getRestoran().getIdRestorana());
			List<Lokacija> lokacije = new ArrayList<>();
			Iterator<Restoran_se_nalazi> nalaziIterator = nalazi.iterator();
			while (nalaziIterator.hasNext()) {
				lokacije.add(nalaziIterator.next().getLokacija());
			}

			List<LokacijaDTO> lokacijeDTO = ObjectMapperUtils.mapAll(lokacije, LokacijaDTO.class);
			restoranDTO.setLokacije(lokacijeDTO);

			// tipovi
			Collection<Je_tipa> jeTipa = this.jeTipaRepository
					.findByRestoran_idRestorana(zaposleni.getRestoran().getIdRestorana());
			List<Tip_restorana> tipovi = new ArrayList<>();
			Iterator<Je_tipa> jeTipaIterator = jeTipa.iterator();
			while (jeTipaIterator.hasNext()) {
				tipovi.add(jeTipaIterator.next().getTipRestorana());
			}
			List<TipRestoranaDTO> tipoviDTO = ObjectMapperUtils.mapAll(tipovi, TipRestoranaDTO.class);
			restoranDTO.setTipRestorana(tipoviDTO);

			return new ResponseEntity<RestoranDTO>(restoranDTO, HttpStatus.OK);

		}
		return null;
	}

	@ApiOperation("Izmena restorana")
	@PutMapping("restoran-zaposleni")
	public ResponseEntity<PutRestoranDTO> updateRestoran(@Validated @RequestBody PutRestoranDTO restoran)
			throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			// izvlacenje zaposlenog
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());

			// save restorana
			Restoran restoranEntity = ObjectMapperUtils.map(restoran, Restoran.class);
			restoranEntity.setIdRestorana(zaposleni.getRestoran().getIdRestorana());
			this.restoranRepository.save(restoranEntity);

			return new ResponseEntity<PutRestoranDTO>(restoran, HttpStatus.CREATED);
		}
		return null;
	}

	@Transactional
	@ApiOperation("Brisanje lokacija restorana")
	@DeleteMapping("restoran-lokacije/{id}")
	public ResponseEntity<String> deleteLokacijeRestoran(@PathVariable int id) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			// izvlacenje zaposlenog
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());

			// brisanje veza lokacija-
			Long count = this.restoranSeNalaziRepository
					.deleteByRestoran_idRestoranaAndLokacija_idLokacije(zaposleni.getRestoran().getIdRestorana(), id);
			if (count > 0) {
				this.lokacijaRepository.deleteById(id);
				return new ResponseEntity<String>("Uspesno obrisano", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Restoran ne sadrzi lokaciju sa datim id-jem", HttpStatus.NOT_FOUND);
			}
		}
		return null;
	}

	@Transactional
	@ApiOperation("Dodavanje lokacija restorana")
	@PostMapping("restoran-lokacije")
	public ResponseEntity<String> addLokacijeRestoran(@Validated @RequestBody LokacijaNoIdDTO lokacija)
			throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			// izvlacenje zaposlenog
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());

			// restoran
			Restoran restoran = zaposleni.getRestoran();

			// lokacija
			Lokacija lokacijaEntity = ObjectMapperUtils.map(lokacija, Lokacija.class);
			this.lokacijaRepository.save(lokacijaEntity);

			Restoran_se_nalazi restoran_se_nalazi = new Restoran_se_nalazi();
			restoran_se_nalazi.setLokacija(lokacijaEntity);
			restoran_se_nalazi.setRestoran(restoran);
			this.restoranSeNalaziRepository.save(restoran_se_nalazi);
			return new ResponseEntity<String>("Uspesno dodata lokacija restorana", HttpStatus.OK);
		}
		return null;
	}

	@Transactional
	@ApiOperation("Brisanje tipa restorana")
	@DeleteMapping("restoran-tip/{id}")
	public ResponseEntity<String> deleteTipRestoran(@PathVariable int id) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			// izvlacenje zaposlenog
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());

			// brisanje veza lokacija-
			Long count = this.jeTipaRepository.deleteByRestoran_idRestoranaAndTipRestorana_idTipaRestorana(
					zaposleni.getRestoran().getIdRestorana(), id);
			if (count > 0) {
				return new ResponseEntity<String>("Uspesno obrisano", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Restoran ne sadrzi tip sa datim id-jem", HttpStatus.NOT_FOUND);
			}
		}
		return null;
	}

	@Transactional
	@ApiOperation("Dodavanje tipa restorana")
	@PostMapping("restoran-tip")
	public ResponseEntity<String> addTipRestoran(@Validated @RequestBody TipRestoranaDTO tip) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			// izvlacenje zaposlenog
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());

			// restoran
			Restoran restoran = zaposleni.getRestoran();

			// provera da li postoji tip
			Optional<Tip_restorana> tipEntity = this.tipRestoranaRepository.findById(tip.getIdTipaRestorana());
			if (!tipEntity.isPresent()) {
				throw new CustomException("Ne postoji tip restorana sa datim id-jem");
			}

			// provera da li je restoran vec tog tipa
			Collection<Je_tipa> jeTipa = this.jeTipaRepository
					.findByRestoran_idRestoranaAndTipRestorana_idTipaRestorana(restoran.getIdRestorana(),
							tipEntity.get().getIdTipaRestorana());
			if (!(jeTipa.size() == 0)) {
				throw new CustomException("Restoran vec ima taj tip");
			}

			Je_tipa veza = new Je_tipa();
			veza.setRestoran(restoran);
			veza.setTipRestorana(tipEntity.get());
			this.jeTipaRepository.save(veza);

			return new ResponseEntity<String>("Uspesno dodat tip", HttpStatus.OK);
		}
		return null;
	}

	@Transactional
	@ApiOperation("Brisanje radnog vremena restorana")
	@DeleteMapping("restoran-vreme/{id}")
	public ResponseEntity<String> deleteVremeRestoran(@PathVariable int id) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			// izvlacenje zaposlenog
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());

			// brisanje veza lokacija-
			Long count = this.radnoVremeRepository.deleteByRestoran_idRestoranaAndTipDatuma_idTipaDatuma(
					zaposleni.getRestoran().getIdRestorana(), id);
			if (count > 0) {
				return new ResponseEntity<String>("Uspesno obrisano", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Restoran ne sadrzi tip sa datim id-jem", HttpStatus.NOT_FOUND);
			}
		}
		return null;
	}

	@Transactional
	@ApiOperation("Dodavanje radnog vremena restorana")
	@PostMapping("restoran-vreme")
	public ResponseEntity<String> addVremeRestoran(@Validated @RequestBody RadnoVremeDTO vreme) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			// izvlacenje zaposlenog
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());

			// restoran
			Restoran restoran = zaposleni.getRestoran();

			// provera da li postoji tip datuma
			Optional<Tip_datuma> tipEntity = this.tipDatumaRepository.findById(vreme.getTipDatuma().getIdTipaDatuma());
			if (!tipEntity.isPresent()) {
				throw new CustomException("Ne postoji tip datuma sa datim id-jem");
			}

			// provera da li je restoran vec tog tipa
			Collection<RadnoVreme> radnoVreme = this.radnoVremeRepository
					.findByRestoran_idRestoranaAndTipDatuma_idTipaDatuma(restoran.getIdRestorana(),
							tipEntity.get().getIdTipaDatuma());

			if (!(radnoVreme.size() == 0)) {
				RadnoVreme current = radnoVreme.iterator().next();
				current.setVremeDo(vreme.getVremeDo());
				current.setVremeOd(vreme.getVremeOd());
				this.radnoVremeRepository.save(current);
			} else {
				RadnoVreme current = new RadnoVreme();
				current.setRestoran(restoran);
				current.setTipDatuma(tipEntity.get());
				current.setVremeDo(vreme.getVremeDo());
				current.setVremeOd(vreme.getVremeOd());
				this.radnoVremeRepository.save(current);
			}

			return new ResponseEntity<String>("Uspesno dodato radno vreme", HttpStatus.OK);
		}
		return null;
	}


}
