package com.eatin.controllers.artikl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.PrilogDTO;
import com.eatin.dto.artikl.ArtiklDTO;
import com.eatin.dto.artikl.ArtiklNoIdDTO;
import com.eatin.dto.artikl.PutArtiklDTO;
import com.eatin.dto.mera.MeraDTO;
import com.eatin.error.CustomException;
import com.eatin.jpa.Artikl;
import com.eatin.jpa.Korisnik;
import com.eatin.jpa.Mera;
import com.eatin.jpa.Moze_biti_mere;
import com.eatin.jpa.Moze_sadrzati_priloge;
import com.eatin.jpa.Prilog;
import com.eatin.jpa.Restoran;
import com.eatin.jpa.Tip_artikla;
import com.eatin.jpa.Zaposleni;
import com.eatin.repository.ArtiklRepository;
import com.eatin.repository.KorisnikRepository;
import com.eatin.repository.MeraRepository;
import com.eatin.repository.MozeBitiMereRepository;
import com.eatin.repository.MozeSadrzatiPrilogeRepository;
import com.eatin.repository.PrilogRepository;
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
	@Autowired
	private PrilogRepository prilogRepository;
	@Autowired
	private MeraRepository meraRepository;
	@Autowired
	private MozeBitiMereRepository mozeBitiMereRepository;
	@Autowired
	private MozeSadrzatiPrilogeRepository mozeSadrzatiPrilogeRepository;

	@ApiOperation("Kreira novi artikl")
	@PostMapping("artikl-zaposleni")
	@Transactional
	public ResponseEntity<ArtiklNoIdDTO> createArtikl(@RequestBody ArtiklNoIdDTO artikl) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			// izvlacenje zaposlenog
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());
			Restoran restoran = zaposleni.getRestoran();

			// osnovne informacije
			Artikl entity = new Artikl();
			entity.setCenaArtikla(artikl.getCenaArtikla());
			entity.setNazivArtikla(artikl.getNazivArtikla());
			entity.setSlikaArtikla(artikl.getSlikaArtikla());
			entity.setRestoran(restoran);

			// tip artikla
			Optional<Tip_artikla> tip = tipArtiklaRepository.findById(artikl.getTipArtikla().getIdTipaArtikla());
			if (!tip.isPresent()) {
				throw new CustomException("Ne postoji tip artikla sa tim id-jem");
			}
			entity.setTipArtikla(tip.get());

			artiklRepository.save(entity);

			// prilozi
			Iterator<PrilogDTO> prilogIterator = artikl.getPrilozi().iterator();
			while (prilogIterator.hasNext()) {
				PrilogDTO prilog = prilogIterator.next();
				Optional<Prilog> prilogEntity = this.prilogRepository.findById(prilog.getIdPriloga());
				if (!prilogEntity.isPresent()) {
					throw new CustomException("Ne postoji prilog sa datim id-jem");
				}
				Moze_sadrzati_priloge imaPriloge = new Moze_sadrzati_priloge();
				imaPriloge.setArtikl(entity);
				imaPriloge.setPrilog(prilogEntity.get());
				this.mozeSadrzatiPrilogeRepository.save(imaPriloge);
			}

			// mere
			Iterator<MeraDTO> meraIterator = artikl.getMere().iterator();
			while (meraIterator.hasNext()) {
				MeraDTO mera = meraIterator.next();
				Optional<Mera> meraEntity = this.meraRepository.findById(mera.getIdMere());
				if (!meraEntity.isPresent()) {
					throw new CustomException("Ne postoji mera sa datim id-jem");
				}
				Moze_biti_mere imaMere = new Moze_biti_mere();
				imaMere.setArtikl(entity);
				imaMere.setMera(meraEntity.get());
				this.mozeBitiMereRepository.save(imaMere);
			}
			return new ResponseEntity<ArtiklNoIdDTO>(artikl, HttpStatus.CREATED);
		}
		return null;
	}

	@ApiOperation("Menja artikl sa datim id-jem")
	@PutMapping("artikl-zaposleni/{id}")
	@Transactional
	public ResponseEntity<ArtiklDTO> updateArtikl(@PathVariable int id, @RequestBody PutArtiklDTO artikl)
			throws Exception {

		// provera da li postoji ovaj artikl
		Optional<Artikl> entity = this.artiklRepository.findById(id);
		if (!entity.isPresent()) {
			throw new CustomException("Ne postoji artikl sa datim id-jem");
		}

		// provera da li je artikl iz restorana u kom radi zaposleni
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());
			Restoran restoran = zaposleni.getRestoran();
			if (!(restoran.getIdRestorana() == entity.get().getRestoran().getIdRestorana())) {
				throw new CustomException("Artikl nije iz restorana u kom radi zaposleni");
			}

			// menjanje artikla
			entity.get().setCenaArtikla(artikl.getCenaArtikla());
			entity.get().setSlikaArtikla(artikl.getSlikaArtikla());
			entity.get().setNazivArtikla(artikl.getNazivArtikla());

			// tip artikla
			Optional<Tip_artikla> tip = tipArtiklaRepository.findById(artikl.getTipArtikla().getIdTipaArtikla());
			if (!tip.isPresent()) {
				throw new CustomException("Ne postoji tip artikla sa tim id-jem");
			}
			entity.get().setTipArtikla(tip.get());

			// cuvanje artikla
			this.artiklRepository.save(entity.get());

			return new ResponseEntity<ArtiklDTO>(ObjectMapperUtils.map(entity.get(), ArtiklDTO.class), HttpStatus.OK);
		}
		return null;
	}

	@ApiOperation("Brise mera sa artikla")
	@DeleteMapping("artikl-mera")
	@Transactional
	public ResponseEntity<String> deleteMera(@RequestParam int idArtikla, @RequestParam int idMere) throws Exception {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			// izvlacenje zaposlenog
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());

			// provera da li artikl postoji
			Optional<Artikl> artikl = this.artiklRepository.findById(idArtikla);
			if (!artikl.isPresent()) {
				throw new CustomException("Ne postoji artikl sa datim id-jem");
			}

			// provera da li artikl pripada restoranu u kome zaposleni radi
			if (!(artikl.get().getRestoran().getIdRestorana() == zaposleni.getRestoran().getIdRestorana())) {
				throw new CustomException("Artikl ne pripada restoranu u kome radi zaposleni");
			}

			// brisanje veza lokacija-
			Long count = this.mozeBitiMereRepository.deleteByArtikl_idArtiklaAndMera_idMere(idArtikla, idMere);
			if (count > 0) {
				return new ResponseEntity<String>("Uspesno obrisano", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Artikl ne sadrzi datu meru", HttpStatus.NOT_FOUND);
			}
		}
		return null;
	}

	@ApiOperation("Dodaje meru artiklu")
	@PostMapping("artikl-mera/{artiklId}")
	public ResponseEntity<String> addMera(@PathVariable int artiklId, @RequestBody MeraDTO mera) throws Exception {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			// izvlacenje zaposlenog
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());

			// provera da li artikl postoji
			Optional<Artikl> artikl = this.artiklRepository.findById(artiklId);
			if (!artikl.isPresent()) {
				throw new CustomException("Ne postoji artikl sa datim id-jem");
			}

			// provera da li artikl pripada restoranu u kome zaposleni radi
			if (!(artikl.get().getRestoran().getIdRestorana() == zaposleni.getRestoran().getIdRestorana())) {
				throw new CustomException("Artikl ne pripada restoranu u kome radi zaposleni");
			}

			// provera da li mera postoji
			Optional<Mera> fetchedMera = this.meraRepository.findById(mera.getIdMere());
			if (!fetchedMera.isPresent()) {
				throw new CustomException("Ne postoji mera sa datim id-jem");
			}

			// provera da li postoji veza izmedju mere i artikla
			Collection<Moze_biti_mere> moze = this.mozeBitiMereRepository
					.findByArtikl_IdArtiklaAndMera_idMere(artikl.get().getIdArtikla(), fetchedMera.get().getIdMere());
			if (moze.size() > 0) {
				throw new CustomException("Artikl vec sadrzi ovu meru");
			}

			// cuavnje u bazi
			Moze_biti_mere veza = new Moze_biti_mere();
			veza.setArtikl(artikl.get());
			veza.setMera(fetchedMera.get());
			this.mozeBitiMereRepository.save(veza);

			// vracanje
			return new ResponseEntity<String>("Uspesno dodato", HttpStatus.OK);

		}
		return null;
	}

	
	@ApiOperation("Brise prilog sa artikla")
	@DeleteMapping("artikl-prilog")
	@Transactional
	public ResponseEntity<String> deletePrilog(@RequestParam int idArtikla, @RequestParam int idPriloga) throws Exception {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			// izvlacenje zaposlenog
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());

			// provera da li artikl postoji
			Optional<Artikl> artikl = this.artiklRepository.findById(idArtikla);
			if (!artikl.isPresent()) {
				throw new CustomException("Ne postoji artikl sa datim id-jem");
			}

			// provera da li artikl pripada restoranu u kome zaposleni radi
			if (!(artikl.get().getRestoran().getIdRestorana() == zaposleni.getRestoran().getIdRestorana())) {
				throw new CustomException("Artikl ne pripada restoranu u kome radi zaposleni");
			}

			// brisanje veze artikla i priloga
			Long count = this.mozeSadrzatiPrilogeRepository.deleteByArtikl_idArtiklaAndPrilog_idPriloga(idArtikla, idPriloga);
			if (count > 0) {
				return new ResponseEntity<String>("Uspesno obrisano", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Artikl ne sadrzi dati prilog", HttpStatus.NOT_FOUND);
			}
		}
		return null;
	}
	
	@ApiOperation("Dodaje prilog artiklu")
	@PostMapping("artikl-prilog/{artiklId}")
	public ResponseEntity<String> addPrilog(@PathVariable int artiklId, @RequestBody PrilogDTO prilog) throws Exception {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			// izvlacenje zaposlenog
			String username = ((UserDetails) principal).getUsername();
			Korisnik korisnik = this.korisnikRepository.findByEmailKorisnika(username);
			Zaposleni zaposleni = this.zaposleniRepository.getOne(korisnik.getIdKorisnika());

			// provera da li artikl postoji
			Optional<Artikl> artikl = this.artiklRepository.findById(artiklId);
			if (!artikl.isPresent()) {
				throw new CustomException("Ne postoji artikl sa datim id-jem");
			}

			// provera da li artikl pripada restoranu u kome zaposleni radi
			if (!(artikl.get().getRestoran().getIdRestorana() == zaposleni.getRestoran().getIdRestorana())) {
				throw new CustomException("Artikl ne pripada restoranu u kome radi zaposleni");
			}

			// provera da li prilog postoji
			Optional<Prilog> fetchedPrilog = this.prilogRepository.findById(prilog.getIdPriloga());
			if (!fetchedPrilog.isPresent()) {
				throw new CustomException("Ne postoji prilog sa datim id-jem");
			}

			// provera da li postoji veza izmedju mere i artikla
			Collection<Moze_sadrzati_priloge> moze = this.mozeSadrzatiPrilogeRepository
					.findByArtikl_IdArtiklaAndPrilog_idPriloga
					(artikl.get().getIdArtikla(), fetchedPrilog.get().getIdPriloga());
			if (moze.size() > 0) {
				throw new CustomException("Artikl vec sadrzi ovaj prilog");
			}

			// cuavnje u bazi
			Moze_sadrzati_priloge veza = new Moze_sadrzati_priloge();
			veza.setArtikl(artikl.get());
			veza.setPrilog(fetchedPrilog.get());
			this.mozeSadrzatiPrilogeRepository.save(veza);

			// vracanje
			return new ResponseEntity<String>("Uspesno dodato", HttpStatus.OK);

		}
		return null;
	}
}
