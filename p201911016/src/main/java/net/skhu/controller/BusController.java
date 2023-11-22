package net.skhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.skhu.repository.BusRepository;

@Controller
public class BusController {

    @Autowired
    BusRepository busRepository;

    @RequestMapping("bus/list1")
    public String list1(Model model) {
        model.addAttribute("buses", busRepository.findByBusNo("27"));
        return "bus/list";
    }

    @RequestMapping("bus/list2")
    public String list2(Model model) {
        model.addAttribute("buses", busRepository.findByFirstStop("온수동"));
        return "bus/list";
    }

    @RequestMapping("bus/list3")
    public String list3(Model model) {
        model.addAttribute("buses", busRepository.findByCategoryId(2));
        return "bus/list";
    }

    @RequestMapping("bus/list4")
    public String list4(Model model) {
        model.addAttribute("buses", busRepository.findByCategoryCategoryName("지선"));
        return "bus/list";
    }

    @RequestMapping("bus/list5")
    public String list5(Model model) {
        model.addAttribute("buses", busRepository.findAllByOrderByFirstBusAsc());
        return "bus/list";
    }
}