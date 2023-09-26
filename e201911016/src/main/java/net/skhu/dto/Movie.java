package net.skhu.dto;
import lombok.Data;

@Data
public class Movie {
	int id;
	String title;
	String director;
	int genreId;
	int year;
	String country;
	
	String genreName;
}