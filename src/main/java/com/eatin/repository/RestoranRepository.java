package com.eatin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eatin.jpa.Restoran;

public interface RestoranRepository extends JpaRepository<Restoran, Integer> {

	Page<Restoran> findByjeTipas_tipRestorana_idTipaRestorana(int tipRestorana, Pageable pageable);
}
