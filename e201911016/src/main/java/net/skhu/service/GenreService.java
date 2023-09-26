package net.skhu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.skhu.dto.Genre;
import net.skhu.mapper.GenreMapper;

@Service
public class GenreService {
	@Autowired
	public GenreMapper GenreMapper;

	public List<Genre> findAll() {
		return GenreMapper.findAll();
	}
}