package com.example.demo.Controllers;

import com.example.demo.Constants.UserSkills;
import com.example.demo.Entities.Event;
import com.example.demo.Entities.User;
import com.example.demo.Entities.UserEvent;
import com.example.demo.Entities.UserSkill;
import com.example.demo.Repositories.EventRepository;
import com.example.demo.Repositories.UserEventRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Repositories.UserSkillRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EventController {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserEventRepository userEventRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserSkillRepository userSkillRepository;
    @GetMapping("/home")
    public String getHomeTemplate(Model model, HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderUsername = authentication.getName();

        User user = userRepository.findByUsername(senderUsername);
//        if (user==null){
//            return "redirect:/event/login";
//        }
        ////////////////////////////
        //UserEvent userEventToNotShow = userEventRepository.findByUser(user);
//        if(userEventToNotShow != null) {
//            Event eventNotToShow = userEventToNotShow.getEvent();
//            LocalDateTime localDateTime = LocalDateTime.now();
//            List<Event> allEvents = (List<Event>) eventRepository.findAll();
//            List<Event> selectedEvents = new ArrayList<>();
//            for (int i = 0; i < allEvents.size(); i++) {
//                if(allEvents.get(i).getStarterTime().isBefore(localDateTime) && allEvents.get(i).getEndTime().isAfter(localDateTime) && allEvents.get(i) != eventNotToShow){
//                    selectedEvents.add(allEvents.get(i));
//                }
//            }
//            model.addAttribute("events", selectedEvents);
//            return "home";
//        }
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Event> allEvents = (List<Event>) eventRepository.findAll();
        List<Event> selectedEvents = new ArrayList<>();
        for (int i = 0; i < allEvents.size(); i++) {
            if(allEvents.get(i).getStarterTime().isBefore(localDateTime) && allEvents.get(i).getEndTime().isAfter(localDateTime)){
                selectedEvents.add(allEvents.get(i));
            }
        }
        model.addAttribute("events", selectedEvents);
        return "home";
    }
    @GetMapping("/view")
    public String viewEventForm(@RequestParam String name,
                                @RequestParam(required = false) List<String> skills,
                                Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderUsername = authentication.getName();
        User user = userRepository.findByUsername(senderUsername);
        Event event = eventRepository.findByName(name);

        if (event == null) {
            return "error";
        }

        model.addAttribute("event", event);

        // Get all users participating in the event
        List<UserEvent> userEvents = (List<UserEvent>) userEventRepository.findAllByEvent(event);
        List<User> users = new ArrayList<>();
        for (UserEvent userEvent : userEvents) {
            if (userEvent.getUser() != null && !userEvent.getUser().equals(user)) {
                users.add(userEvent.getUser());
            }
        }

        // Convert selected skills from String to UserSkills enum
        List<UserSkills> selectedSkills = new ArrayList<>();
        if (skills != null && !skills.isEmpty()) {
            selectedSkills = skills.stream()
                    .map(UserSkills::fromValue)
                    .collect(Collectors.toList());
        }

        // Filter users based on selected skills
        List<User> filteredUsers;
        if (!selectedSkills.isEmpty()) {
            List<UserSkills> finalSelectedSkills = selectedSkills;
            filteredUsers = users.stream()
                    .filter(userr -> userr.getSkills().containsAll(finalSelectedSkills))
                    .collect(Collectors.toList());
        } else {
            filteredUsers = users; // if no skills are selected, keep all users
        }

        model.addAttribute("users", filteredUsers);

        return "view";
    }



    @PostMapping("/view")
    public String viewEvent(@RequestParam String eventName, Model model, HttpSession session) {
        //List<Event> checkInsToDelete= eventRepository.findAllByUser(user);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderUsername = authentication.getName();

        User user = userRepository.findByUsername(senderUsername);
        if (user==null){
            return "redirect:/event/login";
        }
        UserEvent userEventToDelete = userEventRepository.findByUser(user);
        if(userEventToDelete != null){
            userEventRepository.delete(userEventToDelete);
        }
        Event event = eventRepository.findByName(eventName);
        if (event == null) {
            return "error";
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        if(localDateTime.isBefore(event.getStarterTime())){
           return "error";
        }
        userEventRepository.save(new UserEvent(user, event));
        return "redirect:/home";
    }
    @GetMapping("/event/add")
    public String addEvent(Model model){
        model.addAttribute("event", new Event());
        return "add-event-form";
    }
    @PostMapping("/save-event")
    public String saveEvent(@ModelAttribute("event") Event event, Model model, HttpSession session) {
        if(event.getStarterTime().isAfter(event.getEndTime())){
            return "redirect:/event/add";
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        if(event.getEndTime().isBefore(localDateTime)){
            return "redirect:/event/add";
        }

        eventRepository.save(event);

        User user=(User) session.getAttribute("user");
        UserEvent userEvent = new UserEvent();
        userEvent.setUser(user);
        userEvent.setEvent(event);


        userEventRepository.save(userEvent);


        return "redirect:/home";
    }
}
