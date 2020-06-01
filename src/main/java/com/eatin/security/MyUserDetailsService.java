package com.eatin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eatin.jpa.Korisnik;
import com.eatin.repository.KorisnikRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	KorisnikRepository korisnikRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Korisnik korisnik = korisnikRepository.findByEmailKorisnika(username);

		if (korisnik == null) {
			throw new UsernameNotFoundException("Not found " + username);
		}
		return new MyUserDetails(korisnik);
	}
}
