package com.example.demo.Controllers;

import com.example.demo.Repositories.EventRepository;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {
    EventRepository eventRepository;
    @GetMapping("/home")
    public String getHomeTemplate(Model model){
        model.addAttribute("events",eventRepository.findAll());
        return "home";
    }
}
