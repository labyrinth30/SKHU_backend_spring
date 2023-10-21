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
import lombok.extern.slf4j.Slf4j;
import net.skhu.dto.Department;
import net.skhu.dto.Student;
import net.skhu.model.Pagination;
import net.skhu.model.StudentEdit;
import net.skhu.service.DepartmentService;
import net.skhu.service.StudentService;

@Slf4j
@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired StudentService studentService;
    @Autowired DepartmentService departmentService;

    @GetMapping("list")
    public String list(Model model, Pagination pagination) {
        List<Student> students = studentService.findAll(pagination);
        model.addAttribute("students", students);
        model.addAttribute("orders", studentService.getOrders());
        return "student/list";
    }

    @GetMapping("create")
    public String create(Model model, Pagination pagination) {
        StudentEdit studentEdit = new StudentEdit();
        List<Department> departments = departmentService.findAll();
        model.addAttribute("studentEdit", studentEdit);
        model.addAttribute("departments", departments);
        return "student/edit";
    }

    @PostMapping("create")
    public String create(Model model, Pagination pagination,
            @Valid StudentEdit studentEdit, BindingResult bindingResult) {
        try {
            studentService.insert(studentEdit, bindingResult, pagination);
            return "redirect:list?" + pagination.getQueryString();
        }
        catch (Exception exception) {
            model.addAttribute("departments", departmentService.findAll());
            bindingResult.rejectValue("", null, "등록할 수 없습니다.");
            log.error("등록 실패 {}", studentEdit, exception);
            return "student/edit";
        }
    }

    @GetMapping("edit")
    public String edit(Model model, int id, Pagination pagination) {
        StudentEdit studentEdit = studentService.findOne(id);
        List<Department> departments = departmentService.findAll();
        model.addAttribute("studentEdit", studentEdit);
        model.addAttribute("departments", departments);
        return "student/edit";
    }

    @PostMapping(value="edit", params="cmd=save")
    public String edit(Model model, Pagination pagination,
            @Valid StudentEdit studentEdit, BindingResult bindingResult) {
        try {
            studentService.update(studentEdit, bindingResult);
            return "redirect:list?" + pagination.getQueryString();
        }
        catch (Exception exception) {
            model.addAttribute("departments", departmentService.findAll());
            bindingResult.rejectValue("", null, "수정할 수 없습니다.");
            log.error("수정 실패 {}", studentEdit, exception);
            return "student/edit";
        }
    }

    @PostMapping(value="edit", params="cmd=delete")
    public String delete(Model model, Pagination pagination,
            StudentEdit studentEdit, BindingResult bindingResult) {
        try {
            studentService.delete(studentEdit.getId());
            return "redirect:list?" + pagination.getQueryString();
        }
        catch (Exception exception) {
            model.addAttribute("departments", departmentService.findAll());
            bindingResult.rejectValue("", null, "삭제할 수 없습니다.");
            log.error("삭제 실패 {}", studentEdit, exception);
            return "student/edit";
        }
    }
}

