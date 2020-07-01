package com.eatin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatin.jpa.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {

	Token findByToken(String token);
}
