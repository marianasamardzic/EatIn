package com.eatin.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatin.jpa.Restoran_se_nalazi;

public interface RestoranSeNalaziRepository extends JpaRepository<Restoran_se_nalazi, Integer> {

	Collection<Restoran_se_nalazi> findByRestoran_idRestorana(int restoran);

	Long deleteByRestoran_idRestoranaAndLokacija_idLokacije(int restoranId, int lokacijaId);
}
