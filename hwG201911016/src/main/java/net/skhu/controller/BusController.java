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
import net.skhu.entity.Bus;
import net.skhu.entity.Category;
import net.skhu.model.BusEdit;
import net.skhu.model.Pagination;
import net.skhu.service.BusService;
import net.skhu.service.CategoryService;

@Controller
@RequestMapping("bus")
public class BusController {

    @Autowired BusService busService;
    @Autowired CategoryService categoryService;

    @GetMapping("list")
    public String list(Model model, Pagination pagination) {
        List<Bus> buses = busService.findAll(pagination);
        model.addAttribute("buses", buses);
        model.addAttribute("orders", busService.getOrders());
        return "bus/list";
    }

    @GetMapping("create")
    public String create(Model model, Pagination pagination) {
        BusEdit busEdit = new BusEdit();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("busEdit", busEdit);
        model.addAttribute("categories", categories);
        return "bus/edit";
    }

    @PostMapping("create")
    public String create(Model model, Pagination pagination,
            @Valid BusEdit busEdit, BindingResult bindingResult) {
        try {
            busService.insert(busEdit, bindingResult, pagination);
            return "redirect:list?" + pagination.getQueryString();
        }
        catch (Exception e) {
            model.addAttribute("categories", categoryService.findAll());
            bindingResult.rejectValue("", null, "등록할 수 없습니다.");
            return "bus/edit";
        }
    }

    @GetMapping("edit")
    public String edit(Model model, @RequestParam("id") int id, Pagination pagination) {
    	BusEdit busEdit = busService.findOne(id);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("busEdit", busEdit);
        model.addAttribute("categories", categories);
        return "bus/edit";
    }

    @PostMapping(value="edit", params="cmd=save")
    public String edit(Model model, Pagination pagination,
            @Valid BusEdit busEdit, BindingResult bindingResult) {
        try {
            busService.update(busEdit, bindingResult);
            return "redirect:list?" + pagination.getQueryString();
        }
        catch (Exception e) {
            model.addAttribute("categories", categoryService.findAll());
            bindingResult.rejectValue("", null, "수정할 수 없습니다.");
            return "bus/edit";
        }
    }

    @PostMapping(value="edit", params="cmd=delete")
    public String delete(Model model, Pagination pagination,
            BusEdit busEdit, BindingResult bindingResult) {
        try {
            busService.delete(busEdit.getId());
            return "redirect:list?" + pagination.getQueryString();
        }
        catch (Exception e) {
            model.addAttribute("categories", categoryService.findAll());
            bindingResult.rejectValue("", null, "삭제할 수 없습니다.");
            return "student/edit";
        }
    }
}

