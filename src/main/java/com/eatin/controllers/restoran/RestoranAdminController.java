package com.eatin.controllers.restoran;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eatin.common.ObjectMapperUtils;
import com.eatin.dto.LokacijaNoIdDTO;
import com.eatin.dto.restoran.RadnoVremeDTO;
import com.eatin.dto.restoran.RestoranNoIdDTO;
import com.eatin.dto.restoran.SimpleRestoranDTO;
import com.eatin.dto.restoran.TipRestoranaDTO;
import com.eatin.error.CustomException;
import com.eatin.jpa.Je_tipa;
import com.eatin.jpa.Lokacija;
import com.eatin.jpa.RadnoVreme;
import com.eatin.jpa.Restoran;
import com.eatin.jpa.Restoran_se_nalazi;
import com.eatin.jpa.Tip_datuma;
import com.eatin.jpa.Tip_restorana;
import com.eatin.repository.JeTipaRepository;
import com.eatin.repository.LokacijaRepository;
import com.eatin.repository.RadnoVremeRepository;
import com.eatin.repository.RestoranRepository;
import com.eatin.repository.RestoranSeNalaziRepository;
import com.eatin.repository.TipDatumaRepository;
import com.eatin.repository.TipRestoranaRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class RestoranAdminController {

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

	@ApiOperation("Kreira novi restoran")
	@Transactional
	@PostMapping("restoran-admin")
	public ResponseEntity<RestoranNoIdDTO> addRestoran(@Validated @RequestBody RestoranNoIdDTO restoran)
			throws Exception {
		// save restorana
		Restoran restoranEntity = new Restoran();
		restoranEntity.setNazivRestorana(restoran.getNazivRestorana());
		restoranEntity.setOpisRestorana(restoran.getOpisRestorana());
		restoranEntity.setPibRestorana(restoran.getPibRestorana());
		restoranEntity.setTelefonRestorana(restoran.getTelefonRestorana());
		restoranEntity.setSlikaRestorana(restoran.getSlikaRestorana());
		this.restoranRepository.save(restoranEntity);

		// save radno vreme
		if (restoran.getRadnoVreme().size() > 0) {
			Iterator<RadnoVremeDTO> radnoVremeIterator = restoran.getRadnoVreme().iterator();
			while (radnoVremeIterator.hasNext()) {
				RadnoVreme rv = ObjectMapperUtils.map(radnoVremeIterator.next(), RadnoVreme.class);

				// provera da li postoji tip datuma
				Optional<Tip_datuma> td = this.tipDatumaRepository.findById(rv.getTipDatuma().getIdTipaDatuma());
				if (!td.isPresent()) {
					throw new CustomException("Ne postoji tip datuma sa datim id-jem");
				}

				rv.setRestoran(restoranEntity);
				this.radnoVremeRepository.save(rv);
			}
		}

		// save lokacije
		Iterator<LokacijaNoIdDTO> lokacijeIterator = restoran.getLokacije().iterator();
		while (lokacijeIterator.hasNext()) {
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
		while (iterator.hasNext()) {
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

	@ApiOperation("Vraca id-jeve i nazive svih restorana")
	@GetMapping("restoran-admin")
	public ResponseEntity<List<SimpleRestoranDTO>> getAllSimpleRestoran() {
		List<SimpleRestoranDTO> dtos = ObjectMapperUtils.mapAll(this.restoranRepository.findAll(),
				SimpleRestoranDTO.class);
		return new ResponseEntity<List<SimpleRestoranDTO>>(dtos, HttpStatus.OK);
	}
}
