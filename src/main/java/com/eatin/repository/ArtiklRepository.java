package com.eatin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eatin.jpa.Artikl;

public interface ArtiklRepository extends JpaRepository<Artikl, Integer> {
	Page<Artikl> findBynazivArtiklaContainingIgnoreCase(String naziv, Pageable pageable);

	Page<Artikl> findByTipArtikla_idTipaArtikla(int tipArtikla, Pageable pageable);

	Page<Artikl> findByTipArtikla_idTipaArtiklaAndRestoran_idRestorana(int tipArtikla, int restoran, Pageable pageable);

	Page<Artikl> findByRestoran_idRestorana(int restoran, Pageable pageable);

}
