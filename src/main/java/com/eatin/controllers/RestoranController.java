package com.eatin.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.LokacijaDTO;
import com.eatin.dto.LokacijaNoIdDTO;
import com.eatin.dto.restoran.RestoranDTO;
import com.eatin.dto.restoran.RestoranNoIdDTO;
import com.eatin.dto.restoran.TipRestoranaDTO;
import com.eatin.enums.SortByRestoran;
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

//-obezbedjuje validaciju parametara
@Validated
@RestController
public class RestoranController {

	@Autowired
	private RestoranRepository restoranRepository;
	@Autowired
	private RestoranSeNalaziRepository restoranSeNalaziRepository;
	@Autowired
	private JeTipaRepository jeTipaRepository;
	@Autowired
	private RadnoVremeRepository radnoVremeRepository;
	@Autowired
	private LokacijaRepository lokacijaRepository;
	@Autowired
	private TipDatumaRepository tipDatumaRepository;
	@Autowired
	private TipRestoranaRepository tipRestoranaRepository;
	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private ZaposleniRepository zaposleniRepository;

	@ApiOperation("Izlistava sve restorane")
	@GetMapping("restoran")
	public ResponseEntity<Page<RestoranDTO>> getAllRestoran(@RequestParam(defaultValue = "1") @Min(1) int page,
			@RequestParam(defaultValue = "ID") SortByRestoran sortBy,
			@RequestParam(defaultValue = "false") Boolean descending,
			@RequestParam(required = false) String search,
			@RequestParam(required = false) Integer tipRestorana) {

		// paginacija i sortiranje
		Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(sortBy.label));
		if (descending) {
			pageable = PageRequest.of(page - 1, 5, Sort.by(sortBy.label).descending());
		}

		// pretraga
		if (search == null) {
			search = "";
		}

		// izvlacenje iz baze
		Page<Restoran> restorani;
		if (tipRestorana != null) {
			restorani = this.restoranRepository
					.findByjeTipa_tipRestorana_idTipaRestoranaAndNazivRestoranaContainingIgnoreCase(tipRestorana,
							search, pageable);
		} else {
			restorani = this.restoranRepository.findByNazivRestoranaContainingIgnoreCase(search, pageable);
		}

		// mapiranje u DTO
		Page<RestoranDTO> responsePage = ObjectMapperUtils.mapPage(restorani, RestoranDTO.class);

		Iterator<RestoranDTO> iterator = responsePage.getContent().iterator();
		while (iterator.hasNext()) {
			RestoranDTO restoran = iterator.next();

			Collection<Restoran_se_nalazi> nalazi = this.restoranSeNalaziRepository
					.findByRestoran_idRestorana(restoran.getIdRestorana());
			List<Lokacija> lokacije = new ArrayList<>();
			Iterator<Restoran_se_nalazi> nalaziIterator = nalazi.iterator();
			while (nalaziIterator.hasNext()) {
				lokacije.add(nalaziIterator.next().getLokacija());
			}

			List<LokacijaDTO> lokacijeDTO = ObjectMapperUtils.mapAll(lokacije, LokacijaDTO.class);
			restoran.setLokacije(lokacijeDTO);
		}

		// vracanje Response Entity-ja
		return new ResponseEntity<Page<RestoranDTO>>(responsePage, HttpStatus.OK);
	}

	@ApiOperation("Izlistava restoran sa zadatim id-jem")
	@GetMapping("restoran/{id}")
	public ResponseEntity<RestoranDTO> getRestoranById(@PathVariable int id) throws Exception {

		// izvlacenje iz baze
		Optional<Restoran> restoran = this.restoranRepository.findById(id);

		// provera da li postoji
		if (!restoran.isPresent()) {
			throw new CustomException("Ne postoji restoran sa datim id-jem");
		}

		// mapiranje
		RestoranDTO restoranDTO = ObjectMapperUtils.map(restoran.get(), RestoranDTO.class);

		// lokacije
		Collection<Restoran_se_nalazi> nalazi = this.restoranSeNalaziRepository
				.findByRestoran_idRestorana(restoran.get().getIdRestorana());
		List<Lokacija> lokacije = new ArrayList<>();
		Iterator<Restoran_se_nalazi> nalaziIterator = nalazi.iterator();
		while (nalaziIterator.hasNext()) {
			lokacije.add(nalaziIterator.next().getLokacija());
		}

		List<LokacijaDTO> lokacijeDTO = ObjectMapperUtils.mapAll(lokacije, LokacijaDTO.class);
		restoranDTO.setLokacije(lokacijeDTO);

		return new ResponseEntity<RestoranDTO>(restoranDTO, HttpStatus.OK);

	}

	@ApiOperation("Kreira novi restoran")
	@Transactional
	@PostMapping("restoran-admin")
	public ResponseEntity<RestoranNoIdDTO> addRestoran(@Validated @RequestBody RestoranNoIdDTO restoran) throws Exception {
		// save restorana
		Restoran restoranEntity = ObjectMapperUtils.map(restoran, Restoran.class);
		this.restoranRepository.save(restoranEntity);

		// save radno vreme
		Iterator<RadnoVreme> radnoVremeIterator = restoranEntity.getRadnoVreme().iterator();
		while (radnoVremeIterator.hasNext()) {
			RadnoVreme rv = radnoVremeIterator.next();

			// provera da li postoji tip datuma
			Optional<Tip_datuma> td = this.tipDatumaRepository.findById(rv.getTipDatuma().getIdTipaDatuma());
			if(!td.isPresent()) {
				throw new CustomException("Ne postoji tip datuma sa datim id-jem");
			}

			rv.setRestoran(restoranEntity);
			this.radnoVremeRepository.save(rv);
		}
		
		// save lokacije
		Iterator<LokacijaNoIdDTO> lokacijeIterator = restoran.getLokacije().iterator();
		while(lokacijeIterator.hasNext()) {
			LokacijaNoIdDTO lokacijaDTO = lokacijeIterator.next();
			Lokacija lokacija = ObjectMapperUtils.map(lokacijaDTO, Lokacija.class);
			this.lokacijaRepository.save(lokacija);
			Restoran_se_nalazi nalazi = new Restoran_se_nalazi();
			nalazi.setLokacija(lokacija);
			nalazi.setRestoran(restoranEntity);
			this.restoranSeNalaziRepository.save(nalazi);
		}

		// veza restoran - tip restorana
		Iterator<TipRestoranaDTO> iterator = restoran.getTipRestorana().iterator();
		while(iterator.hasNext()) {
			Tip_restorana tip = ObjectMapperUtils.map(iterator.next(), Tip_restorana.class);

			// provera da li postoji tip restorana
			Optional<Tip_restorana> tipFetched = this.tipRestoranaRepository.findById(tip.getIdTipaRestorana());
			if (!tipFetched.isPresent()) {
				throw new CustomException("Ne postoji tip restorana sa datim id-jem");
			}

			Je_tipa jeTipa = new Je_tipa();
			jeTipa.setRestoran(restoranEntity);
			jeTipa.setTipRestorana(tip);
			jeTipaRepository.save(jeTipa);
		}
		return new ResponseEntity<RestoranNoIdDTO>(restoran, HttpStatus.CREATED);
	}

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

			return new ResponseEntity<RestoranDTO>(restoranDTO, HttpStatus.OK);

		}
		return null;
	}

}
