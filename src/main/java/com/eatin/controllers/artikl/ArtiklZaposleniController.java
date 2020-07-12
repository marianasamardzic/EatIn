package com.eatin.controllers.artikl;

import java.util.Iterator;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.dto.PrilogDTO;
import com.eatin.dto.artikl.ArtiklNoIdDTO;
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

}
