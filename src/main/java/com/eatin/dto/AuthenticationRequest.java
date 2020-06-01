package com.eatin.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class AuthenticationRequest {

	private @NonNull String username;
	private @NonNull String password;
}
