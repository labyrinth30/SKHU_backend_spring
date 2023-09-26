package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.skhu.dto.Movie;

@Mapper
public interface MovieMapper {
	@Select("SELECT * FROM movie WHERE id = #{id}")
	Movie findOne(int id);

	@Select("SELECT * FROM movie WHERE title = #{title}")
	Movie findByTitle(String title);

	@Select("""
			SELECT m.*, g.genreName
			FROM movie m LEFT JOIN genre g ON m.genreId = g.id """)
	List<Movie> findAll();

	@Insert("""
			INSERT movie (title, genreId, year, country, director)
			VALUES ( #{title}, #{genreId}, #{year}, #{country}, #{director}) """)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(Movie movie);

	@Update("""
			UPDATE movie SET
			title = #{title},
			genreId = #{genreId},
			director = #{director},
			year = #{year},
			country = #{country}
			WHERE id = #{id} """)
	void update(Movie movie);

	@Delete("DELETE FROM movie WHERE id = #{id}")
	void delete(int id);
}
