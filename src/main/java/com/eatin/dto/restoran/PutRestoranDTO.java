package com.eatin.dto.restoran;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PutRestoranDTO {

	@NotNull(message = "Polje nazivRestorana je obavezno")
	@NotEmpty(message = "Polje nazivRestorana ne sme biti prazno")
	private String nazivRestorana;
	private String opisRestorana;
	@NotNull(message = "Polje pibRestorana je obavezno")
	@NotEmpty(message = "Polje pibRestorana ne sme biti prazno")
	@Size(min = 9, max = 9, message = "Polje pibRestorana mora sadrzati 9 karaktera")
	@Pattern(regexp = "^[0-9]*$", message = "Polje pibRestorana moze sadrzati samo brojeve")
	private String pibRestorana;
	@NotNull(message = "Polje slikaRestorana je obavezno")
	@NotEmpty(message = "Polje slikaRestorana ne sme biti prazno")
	private String slikaRestorana;
	@NotNull(message = "Polje telefonRestorana je obavezno")
	@NotEmpty(message = "Polje telefonRestorana ne sme biti prazno")
	private String telefonRestorana;

}
