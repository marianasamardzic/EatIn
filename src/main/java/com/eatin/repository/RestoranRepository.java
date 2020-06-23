package com.eatin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eatin.jpa.Restoran;

public interface RestoranRepository extends JpaRepository<Restoran, Integer> {

	Page<Restoran> findByjeTipa_tipRestorana_idTipaRestoranaAndNazivRestoranaContainingIgnoreCase(int tipRestorana,
			String naziv, Pageable pageable);

	Page<Restoran> findByNazivRestoranaContainingIgnoreCase(String naziv, Pageable pageable);
}
