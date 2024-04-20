package com.example.demo.Controllers;

import com.example.demo.Entities.Event;
import com.example.demo.Entities.User;
import com.example.demo.Entities.UserEvent;
import com.example.demo.Repositories.EventRepository;
import com.example.demo.Repositories.UserEventRepository;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EventController {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserEventRepository userEventRepository;
    @Autowired
    UserRepository userRepository;
    @GetMapping("/home")
    public String getHomeTemplate(Model model){
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Event> allEvents = (List<Event>) eventRepository.findAll();
        List<Event> selectedEvents = new ArrayList<>();
        for (int i = 0; i < allEvents.size(); i++) {
            if(allEvents.get(i).getStarterTime().isBefore(localDateTime) && allEvents.get(i).getEndTime().isAfter(localDateTime) ){
                selectedEvents.add(allEvents.get(i));
            }
        }
        model.addAttribute("events", selectedEvents);
        return "home";
    }
    @GetMapping("/view")
    public String viewEventForm(@RequestParam String name, Model model) {
        Event event = eventRepository.findByName(name);
        if (event == null) {
            return "error";
        }
        model.addAttribute("event", event);
        return "view";
    }
    @PostMapping("/view")
    public String viewEvent(@RequestParam String eventName, Model model) {
        //List<Event> checkInsToDelete= eventRepository.findAllByUser(user);
        Event event = eventRepository.findByName(eventName);
        if (event == null) {
            return "error";
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        if(localDateTime.isBefore(event.getStarterTime())){
           return "error";
        }
        userEventRepository.save(new UserEvent(userRepository.findById(1L).orElse(null), event));
        return "home";
    }
    @GetMapping("/event/add")
    public String addEvent(Model model){
        model.addAttribute("event", new Event());
        return "add-event-form";
    }
}
