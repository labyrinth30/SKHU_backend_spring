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
import net.skhu.dto.Department;
import net.skhu.dto.Student;
import net.skhu.model.StudentEdit;
import net.skhu.service.DepartmentService;
import net.skhu.service.StudentService;

@Controller
@RequestMapping("student")
public class StudentController {
	@Autowired
	StudentService studentService;
	@Autowired
	DepartmentService departmentService;

	@GetMapping("list")
	public String list(Model model) {
		List<Student> students = studentService.findAll();
		model.addAttribute("students", students);
		return "student/list";
	}

	@GetMapping("create")
	public String create(Model model) {
		StudentEdit studentEdit = new StudentEdit();
		List<Department> departments = departmentService.findAll();
		model.addAttribute("studentEdit", studentEdit);
		model.addAttribute("departments", departments);
		return "student/edit";
	}

	@PostMapping("create")
	public String create(Model model, @Valid StudentEdit studentEdit, BindingResult bindingResult) {
		try {
			studentService.insert(studentEdit, bindingResult);
			return "redirect:list";
		} catch (Exception e) {
			model.addAttribute("departments", departmentService.findAll());
			bindingResult.rejectValue("", null, "등록할 수 없습니다.");
			return "student/edit";
		}
	}

	@GetMapping("edit")
	public String edit(Model model, int id) {
		StudentEdit studentEdit = studentService.findOne(id);
		List<Department> departments = departmentService.findAll();
		model.addAttribute("studentEdit", studentEdit);
		model.addAttribute("departments", departments);
		return "student/edit";
	}

	@PostMapping(value = "edit", params = "cmd=save")
	public String edit(Model model, @Valid StudentEdit studentEdit, BindingResult bindingResult) {

		try {
			studentService.update(studentEdit, bindingResult);
			return "redirect:list";
		} catch (Exception e) {
			model.addAttribute("departments", departmentService.findAll());
			bindingResult.rejectValue("", null, "수정할 수 없습니다.");
			return "student/edit";
		}
	}



	@PostMapping(value = "edit", params = "cmd=delete")
	public String delete(Model model, StudentEdit studentEdit, BindingResult bindingResult) {
		try {
			studentService.delete(studentEdit.getId());
			return "redirect:list";
		} catch (Exception e) {
			model.addAttribute("departments", departmentService.findAll());
			bindingResult.rejectValue("", null, "삭제할 수 없습니다.");
			return "student/edit";
		}
	}
}