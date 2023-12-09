package net.skhu.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.skhu.entity.Book;
import net.skhu.model.Pagination;
import net.skhu.model.BookEdit;
import net.skhu.repository.BookRepository;

@Service
public class BookService {

    @Data @AllArgsConstructor
    public class Order {
        int value;
        String label;
    }

    Order[] orders = new Order[] {
            new Order(0, "정렬 순서"),
            new Order(1, "제목 오름차순"),
            new Order(2, "제목 내림차순"),
            new Order(3, "가격 오름차순"),
            new Order(4, "카테고리 오름차순")
    };

    Sort[] sorts = new Sort[] {
            Sort.by(Sort.Direction.ASC, "id"),
            Sort.by(Sort.Direction.ASC, "title"),
            Sort.by(Sort.Direction.DESC, "title"),
            Sort.by(Sort.Direction.ASC, "price"),
            Sort.by(Sort.Direction.ASC, "category.name"),
    };

    @Autowired BookRepository bookRepository;
    ModelMapper modelMapper = new ModelMapper();

    public Order[] getOrders() {
        return orders;
    }

    public BookEdit findOne(int id) {
        Book bookEntity = bookRepository.findById(id).get();
        return toEditModel(bookEntity);
    }

    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> findAll(Pagination pagination) {
        int orderIndex = pagination.getOd();
        PageRequest pageRequest = PageRequest.of(pagination.getPg() - 1,
                pagination.getSz(), sorts[orderIndex]);
        Page<Book> page;
        if (pagination.getSt().length() == 0)
            page = bookRepository.findAll(pageRequest);
        else
            page = bookRepository.findByTitleOrAuthorStartsWithOrCategoryNameStartsWith(
                    pagination.getSt(), pagination.getSt(), pagination.getSt(), pageRequest);
        pagination.setRecordCount((int)page.getTotalElements());
        return page.getContent();
    }

    public void insert(BookEdit bookEdit, BindingResult bindingResult,
                       Pagination pagination) throws Exception {
        if (hasErrors(bookEdit, bindingResult))
            throw new Exception("입력 데이터 오류");
        Book book = toEntity(bookEdit);
        bookRepository.save(book);
        pagination.setSt("");
        pagination.setOd(0);
        int lastPage = (int)Math.ceil((double)bookRepository.count() / pagination.getSz());
        pagination.setPg(lastPage);
    }

    public void update(BookEdit bookEdit,
                       BindingResult bindingResult) throws Exception {
        if (hasErrors(bookEdit, bindingResult))
            throw new Exception("입력 데이터 오류");
        Book book = toEntity(bookEdit);
        bookRepository.save(book);
    }

    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public Book toEntity(BookEdit bookEdit) {
        return modelMapper.map(bookEdit, Book.class);
    }

    public BookEdit toEditModel(Book bookDto) {
        return modelMapper.map(bookDto, BookEdit.class);
    }

    public boolean hasErrors(BookEdit bookEdit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return true;
        Book book2 = findByTitle(bookEdit.getTitle());
        if (book2 != null && book2.getId() != bookEdit.getId()) {
            bindingResult.rejectValue("title", null, "제목이 중복됩니다.");
            return true;
        }
        return false;
    }
}

