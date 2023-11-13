package net.skhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.skhu.entity.Student;
import net.skhu.repository.StudentRepository;

@Controller
public class StudentController {

    @Autowired StudentRepository studentRepository;

    @RequestMapping("student/test1")
    public String test1(Model model) {
        Student student = studentRepository.findByStudentNo("201732008");
        model.addAttribute("students", student);
        return "student/list";
    }

    @RequestMapping("student/test2")
    public String test2(Model model) {
        model.addAttribute("students",
                studentRepository.findByName("김영수"));
        return "student/list";
    }

    @RequestMapping("student/test3")
    public String test3(Model model) {
        model.addAttribute("students",
                studentRepository.findByNameStartsWith("김"));
        return "student/list";
    }

    @RequestMapping("student/test4")
    public String test4(Model model) {
        model.addAttribute("students",
                studentRepository.findByDepartmentName("소프트웨어공학과"));
        return "student/list";
    }

    @RequestMapping("student/test5")
    public String test5(Model model) {
        model.addAttribute("students",
                studentRepository.findByDepartmentNameStartsWith("소프"));
        return "student/list";
    }

    @RequestMapping("student/test6")
    public String test6(Model model) {
        model.addAttribute("students",
                studentRepository.findByOrderByName());
        return "student/list";
    }

    @RequestMapping("student/test7")
    public String test7(Model model) {
        model.addAttribute("students",
                studentRepository.findByOrderByNameDesc());
        return "student/list";
    }

    @RequestMapping("student/test8")
    public String test8(Model model) {
        model.addAttribute("students",
                studentRepository.findByDepartmentIdOrderByNameDesc(1));
        return "student/list";
    }
}

