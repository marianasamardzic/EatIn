package com.eatin.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eatin.jpa.Korisnik;

public class MyUserDetails implements UserDetails {

	private String username;
	private String password;
	private Boolean active = true;
	private List<GrantedAuthority> authorities;

	public MyUserDetails(Korisnik korisnik) {
		this.username = korisnik.getEmailKorisnika();
		this.password = korisnik.getLozinkaKorisnika();
		this.authorities = new ArrayList<GrantedAuthority>(
				Arrays.asList(new SimpleGrantedAuthority(korisnik.getUloga().getNazivUloge())));
	}

	public MyUserDetails() {
		super();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return active;
	}

}
