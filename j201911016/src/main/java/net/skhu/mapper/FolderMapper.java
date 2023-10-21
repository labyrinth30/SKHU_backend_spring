package net.skhu.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import net.skhu.dto.Folder;
@Mapper
public interface FolderMapper {
 @Select("SELECT * FROM folder")
 List<Folder> findAll();
}