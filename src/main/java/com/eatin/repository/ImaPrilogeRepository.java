package com.eatin.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatin.jpa.Ima_priloge;

public interface ImaPrilogeRepository extends JpaRepository<Ima_priloge, Integer> {

	Collection<Ima_priloge> findByStavkaPorudzbine_idStavkePorudzbine(int sadrzi);
}
