package net.skhu.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import net.skhu.dto.Genre;
@Mapper
public interface GenreMapper {
 @Select("SELECT * FROM genre")
 List<Genre> findAll();
}