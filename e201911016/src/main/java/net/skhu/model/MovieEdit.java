package net.skhu.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MovieEdit {
	int id;
	@NotEmpty
	@NotBlank
	String title;
	@NotEmpty
	@NotBlank
	String director;
	int genreId;
	int year;
	@NotEmpty
	@NotBlank
	String country;
}
