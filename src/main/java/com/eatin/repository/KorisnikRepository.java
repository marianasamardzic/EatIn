package com.eatin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatin.jpa.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
	Korisnik findByEmailKorisnika(String emailKorisnika);
}
