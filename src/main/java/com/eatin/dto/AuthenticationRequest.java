package com.eatin.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AuthenticationRequest {

	@NotEmpty(message = "Polje username ne sme biti prazno")
	@Email(message = "Polje username mora biti u pravilnom formatu")
	@NotNull(message = "Polje username je obavezeno")
	private String username;

	@NotEmpty(message = "Polje password ne sme biti prazno")
	@NotNull(message = "Polje password je obavezeno")
	private String password;
}
