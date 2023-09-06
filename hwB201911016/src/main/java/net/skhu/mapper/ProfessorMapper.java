package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.skhu.dto.Professor;

@Mapper
public interface ProfessorMapper {
	@Select("SELECT * FROM Professor WHERE id = #{id}")
	Professor findOne(int id);

	@Select("""
			SELECT p.*, d.name departmentName
			FROM Professor p LEFT JOIN department d ON p.departmentId = d.id """)
	List<Professor> findAll();

	@Insert("""
			INSERT professor (name, departmentId, phone, email, office)
			VALUES (#{name},  #{departmentId}, #{phone}, #{email}, #{office}) """)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(Professor professor);

	@Update("""
			UPDATE professor SET
			name = #{name},
			departmentId = #{departmentId},
			phone = #{phone},
			email = #{email},
			office = #{office}
			WHERE id = #{id} """)
	void update(Professor professor);

	@Delete("DELETE FROM Professor WHERE id = #{id}")
	void delete(int id);

}