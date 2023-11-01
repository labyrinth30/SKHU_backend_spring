package net.skhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import net.skhu.entity.Department;
import net.skhu.repository.DepartmentRepository;

@Controller
public class DepartmentController {

    @Autowired DepartmentRepository departmentRepository;

    @GetMapping("department/list")
    public String list(Model model) {
    	model.addAttribute("departments", departmentRepository.findAll());
        return "department/list";
    }

    @GetMapping("department/create")
    public String create(Model model) {
    	model.addAttribute("department", new Department());
        return "department/edit";
    }

    @PostMapping("department/create")
    public String create(Model model,Department department) {
        departmentRepository.save(department);
        return "redirect:list";
    }

    @GetMapping("department/edit")
    public String edit(Model model, int id) {
    	model.addAttribute("department", departmentRepository.findById(id).get());
        return "department/edit";
    }

    @PostMapping("department/edit")
    public String edit(Model model,Department department) {
        departmentRepository.save(department);
        return "redirect:list";
    }

}

