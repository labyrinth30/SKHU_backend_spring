package net.skhu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import net.skhu.entity.Category;
import net.skhu.entity.Book;
import net.skhu.model.Pagination;
import net.skhu.model.BookEdit;
import net.skhu.service.CategoryService;
import net.skhu.service.BookService;

@Controller
@RequestMapping("book")
public class BookController {

    @Autowired BookService bookService;
    @Autowired CategoryService categoryService;

    @GetMapping("list")
    public String list(Model model, Pagination pagination) {
        List<Book> books = bookService.findAll(pagination);
        model.addAttribute("books", books);
        model.addAttribute("orders", bookService.getOrders());
        return "book/list";
    }

    @GetMapping("create")
    public String create(Model model, Pagination pagination) {
        BookEdit bookEdit = new BookEdit();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("bookEdit", bookEdit);
        model.addAttribute("categories", categories);
        return "book/edit";
    }

    @PostMapping("create")
    public String create(Model model, Pagination pagination,
                         @Valid BookEdit bookEdit, BindingResult bindingResult) {
        try {
            bookService.insert(bookEdit, bindingResult, pagination);
            return "redirect:list?" + pagination.getQueryString();
        }
        catch (Exception e) {
            model.addAttribute("categories", categoryService.findAll());
            bindingResult.rejectValue("", null, "등록할 수 없습니다.");
            return "book/edit";
        }
    }

    @GetMapping("edit")
    public String edit(Model model, @RequestParam("id") int id, Pagination pagination) {
        BookEdit bookEdit = bookService.findOne(id);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("bookEdit", bookEdit);
        model.addAttribute("categories", categories);
        return "book/edit";
    }

    @PostMapping(value="edit", params="cmd=save")
    public String edit(Model model, Pagination pagination,
                       @Valid BookEdit bookEdit, BindingResult bindingResult) {
        try {
            bookService.update(bookEdit, bindingResult);
            return "redirect:list?" + pagination.getQueryString();
        }
        catch (Exception e) {
            model.addAttribute("categories", categoryService.findAll());
            bindingResult.rejectValue("", null, "수정할 수 없습니다.");
            return "book/edit";
        }
    }

    @PostMapping(value="edit", params="cmd=delete")
    public String delete(Model model, Pagination pagination,
                         BookEdit bookEdit, BindingResult bindingResult) {
        try {
            bookService.delete(bookEdit.getId());
            return "redirect:list?" + pagination.getQueryString();
        }
        catch (Exception e) {
            model.addAttribute("categories", categoryService.findAll());
            bindingResult.rejectValue("", null, "삭제할 수 없습니다.");
            return "book/edit";
        }
    }
}

