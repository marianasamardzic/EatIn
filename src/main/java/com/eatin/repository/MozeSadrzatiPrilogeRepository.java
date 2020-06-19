package com.eatin.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatin.jpa.Moze_sadrzati_priloge;

public interface MozeSadrzatiPrilogeRepository extends JpaRepository<Moze_sadrzati_priloge, Integer> {

	Collection<Moze_sadrzati_priloge> findByArtikl_IdArtikla(int artikl);
}
