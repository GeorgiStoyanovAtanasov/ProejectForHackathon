package com.example.demo.Controllers;

import com.example.demo.Entities.Event;
import com.example.demo.Entities.User;
import com.example.demo.Entities.UserEvent;
import com.example.demo.Repositories.EventRepository;
import com.example.demo.Repositories.UserEventRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.EventService;
import com.example.demo.Utils.UserSkills;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class EventController {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserEventRepository userEventRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    EventService eventService;
    @GetMapping("/home")
    public String getHomeTemplate(Model model, HttpSession session){
        User user=(User) session.getAttribute("user");
        if (user==null){
            return "redirect:/event/login";
        }
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
//-------------------------------------------------------------------------

//    @GetMapping("/event/{id}/participants")
//    public String viewParticipants(@PathVariable Long eventId, @RequestParam(required = false) Set<UserSkills> userSkills, Model model) {
//        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
//
//        List<UserEvent> participants;
//        if (userSkills != null && !userSkills.isEmpty()) {
//            participants = userEventRepository.findByEventAndUsersSkillsIn(event, userSkills);
//        } else {
//            participants = userEventRepository.findByEvent(event);
//        }
//
//        model.addAttribute("event", event);
//        model.addAttribute("participants", participants);
//        return "userEvent";
//    }
}
