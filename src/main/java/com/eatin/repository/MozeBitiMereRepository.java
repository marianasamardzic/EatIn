package com.eatin.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatin.jpa.Moze_biti_mere;

public interface MozeBitiMereRepository extends JpaRepository<Moze_biti_mere, Integer> {

	Collection<Moze_biti_mere> findByArtikl_IdArtikla(int artikl);

	Collection<Moze_biti_mere> findByArtikl_IdArtiklaAndMera_idMere(int artikl, int mera);

	Long deleteByArtikl_idArtiklaAndMera_idMere(int artikl, int mera);
}
