package com.eatin.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatin.jpa.Klijent_se_nalazi;

public interface KlijentSeNalaziRepository extends JpaRepository<Klijent_se_nalazi, Integer> {

	Collection<Klijent_se_nalazi> findByKlijent_idKlijenta(int klijent);
}
