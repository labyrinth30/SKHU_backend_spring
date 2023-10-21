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
import net.skhu.dto.Document;
import net.skhu.model.Pagination;

@Mapper
public interface DocumentMapper {

    @Data
    @AllArgsConstructor
    public class Order {
        int value;
        String label;
    }

    Order[] orders = new Order[] {
        new Order(0, "정렬 순서"),
        new Order(1, "파일명 오름차순"),
        new Order(2, "폴더명 오름차순")
    };

    @Select("""
            SELECT d.*, f.title
            FROM document d LEFT JOIN folder f ON d.folderId = f.id
            WHERE #{st} = '' OR
              d.fileName LIKE CONCAT('%', #{st}, '%') OR
              f.title LIKE CONCAT('%', #{st}, '%')
            ORDER BY
              (CASE WHEN #{od} = 0 THEN d.id END) ASC,
              (CASE WHEN #{od} = 1 THEN d.fileName END) ASC,
              (CASE WHEN #{od} = 2 THEN f.title END) ASC
            LIMIT #{firstRecordIndex}, #{sz} """)
    List<Document> findAll(Pagination pagination);

    @Select("""
            SELECT COUNT(*)
            FROM document d LEFT JOIN folder f ON d.folderId = f.id
            WHERE #{st} = '' OR
              d.fileName LIKE CONCAT('%', #{st}, '%') OR
              f.title LIKE CONCAT('%', #{st}, '%') """)
    int getCount(Pagination pagination);

    @Select("SELECT * FROM document WHERE id = #{id}")
    Document findOne(int id);

    @Select("SELECT * FROM document WHERE fileName = #{fileName}")
    Document findByFileName(String fileName);

    @Insert("""
        INSERT document (fileName, size, folderId, modifiedDate, state)
        VALUES (#{fileName}, #{size}, #{folderId}, #{modifiedDate}, #{state}) """)
    @Options(useGeneratedKeys=true, keyProperty="id")
    void insert(Document document);

    @Update("""
        UPDATE document SET
          fileName= #{fileName},
          size = #{size},
          folderId = #{folderId},
          modifiedDate = #{modifiedDate},
          state = #{state}
        WHERE id = #{id} """)
    void update(Document document);

    @Delete("DELETE FROM document WHERE id = #{id}")
    void delete(int id);
}

