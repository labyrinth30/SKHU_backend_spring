package net.skhu.controller;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeController {
 @GetMapping("/")
 public String index(Model model) {
 model.addAttribute("message", "좋은 아침");
 model.addAttribute("now", new Date());
 return "index";
 }
}