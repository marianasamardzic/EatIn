package com.eatin.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatin.jpa.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
	Korisnik findByEmailKorisnika(String emailKorisnika);

	Collection<Korisnik> findByUloga_idUloge(int uloga);
	
}
