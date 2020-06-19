package com.eatin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eatin.jpa.Porudzbina;

public interface PorudzbinaRepository extends JpaRepository<Porudzbina, Integer> {

	Page<Porudzbina> findByStatusPorudzbineIgnoreCase(String statusPorudzbine, Pageable pageable);

}
