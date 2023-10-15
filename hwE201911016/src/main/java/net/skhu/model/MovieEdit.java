package net.skhu.model;

import jakarta.validation.constraints.Min;
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
	@NotEmpty
	@NotBlank
	String year;
	@NotEmpty
	@NotBlank
	String country;
	@Min(value = 1, message = "장르를 선택하세요.")
	int genreId;
}