package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.skhu.dto.Movie;
import net.skhu.model.Pagination;

@Mapper
public interface MovieMapper {

	@Data
	@AllArgsConstructor
	public class Order {
		int value;
		String label;
	}

	Order[] orders = new Order[] { new Order(0, "정렬 순서"), new Order(1, "장르 내림차순"), new Order(2, "장르 오름차순")

	};

	@Select("""
			SELECT m.*, g.genreName
			FROM movie m LEFT JOIN genre g ON m.genreId = g.id
			WHERE #{st} = '' OR
			  m.title LIKE CONCAT('%', #{st}, '%') OR
			  m.director LIKE CONCAT('%', #{st}, '%')
			ORDER BY
			  CASE WHEN #{od} = 0 THEN m.id END ASC,
			  CASE WHEN #{od} = 1 THEN m.genreId END ASC,
			  CASE WHEN #{od} = 2 THEN m.genreId END DESC

			LIMIT #{firstRecordIndex}, #{sz} """)
	List<Movie> findAll(Pagination pagination);

	@Select("""
			SELECT COUNT(*)
			FROM movie m LEFT JOIN genre g ON m.genreId = g.id
			 WHERE #{st} = '' OR
			  m.title LIKE CONCAT('%', #{st}, '%') OR
			  m.director LIKE CONCAT('%', #{st}, '%')""")
	int getCount(Pagination pagination);

	@Select("SELECT * FROM movie WHERE id = #{id}")
	Movie findOne(int id);

	@Select("SELECT * FROM movie WHERE title = #{title}")
	Movie findByTitle(String title);

	@Insert("""
			INSERT movie (title, director, genreId, year, country)
			VALUES (#{title}, #{director}, #{genreId}, #{year}, #{country})  """)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(Movie movie);

	@Update("""
			UPDATE movie SET
			  title = #{title},
			  director = #{director},
			  genreId = #{genreId},
			  year = #{year},
			  country = #{country}
			WHERE id = #{id} """)
	void update(Movie movie);

	@Delete("DELETE FROM movie WHERE id = #{id}")
	void delete(int id);
}
