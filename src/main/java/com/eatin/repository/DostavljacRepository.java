package com.eatin.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatin.jpa.Dostavljac;
public interface DostavljacRepository extends JpaRepository<Dostavljac, Integer> {

	Collection<Dostavljac> findByKorisnik_idKorisnika(int korisnik);

}
