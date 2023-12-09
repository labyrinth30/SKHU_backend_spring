package net.skhu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.skhu.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer>  {

    Book findByTitle(String title);

    Page<Book> findByTitleOrAuthorStartsWithOrCategoryNameStartsWith(
            String title, String author, String categoryName, Pageable pageable);
}

