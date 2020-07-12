package com.eatin.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatin.jpa.RadnoVreme;

public interface RadnoVremeRepository extends JpaRepository<RadnoVreme, Integer> {

	Collection<RadnoVreme> findByRestoran_idRestorana(int restoran);

	Long deleteByRestoran_idRestoranaAndTipDatuma_idTipaDatuma(int restoran, int datum);

	Collection<RadnoVreme> findByRestoran_idRestoranaAndTipDatuma_idTipaDatuma(int restoran, int datum);
}
