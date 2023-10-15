package net.skhu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import net.skhu.dto.Genre;
import net.skhu.dto.Movie;
import net.skhu.model.MovieEdit;
import net.skhu.model.Pagination;
import net.skhu.service.GenreService;
import net.skhu.service.MovieService;

@Controller
@RequestMapping("movie")
public class MovieController {

    @Autowired MovieService movieService;
    @Autowired GenreService genreService;

    @GetMapping("list")
    public String list(Model model, Pagination pagination) {
        List<Movie> movies = movieService.findAll(pagination);
        model.addAttribute("movies", movies);
        model.addAttribute("orders", movieService.getOrders());
        return "movie/list";
    }

    @GetMapping("create")
    public String create(Model model, Pagination pagination) {
        MovieEdit movieEdit = new MovieEdit();
        List<Genre> genres = genreService.findAll();
        model.addAttribute("movieEdit", movieEdit);
        model.addAttribute("genres", genres);
        return "movie/edit";
    }

    @PostMapping("create")
    public String create(Model model, Pagination pagination,
            @Valid MovieEdit movieEdit, BindingResult bindingResult) {
        try {
            movieService.insert(movieEdit, bindingResult, pagination);
            return "redirect:list?" + pagination.getQueryString();
        }
        catch (Exception e) {
            model.addAttribute("genres", genreService.findAll());
            bindingResult.rejectValue("", null, "등록할 수 없습니다.");
            return "movie/edit";
        }
    }

    @GetMapping("edit")
    public String edit(Model model, int id, Pagination pagination) {
        MovieEdit movieEdit = movieService.findOne(id);
        List<Genre> genres = genreService.findAll();
        model.addAttribute("movieEdit", movieEdit);
        model.addAttribute("genres", genres);
        return "movie/edit";
    }

    @PostMapping(value="edit", params="cmd=save")
    public String edit(Model model, Pagination pagination,
            @Valid MovieEdit movieEdit, BindingResult bindingResult) {
        try {
            movieService.update(movieEdit, bindingResult);
            return "redirect:list?" + pagination.getQueryString();
        }
        catch (Exception e) {
            model.addAttribute("genres", genreService.findAll());
            bindingResult.rejectValue("", null, "수정할 수 없습니다.");
            return "movie/edit";
        }
    }

    @PostMapping(value="edit", params="cmd=delete")
    public String delete(Model model, Pagination pagination,
            MovieEdit movieEdit, BindingResult bindingResult) {
        try {
            movieService.delete(movieEdit.getId());
            return "redirect:list?" + pagination.getQueryString();
        }
        catch (Exception e) {
            model.addAttribute("genres", genreService.findAll());
            bindingResult.rejectValue("", null, "삭제할 수 없습니다.");
            return "movie/edit";
        }
    }
}

