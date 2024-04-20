package com.example.demo.Controllers;

import com.example.demo.Entities.UserEvent;
import com.example.demo.Services.UserEventService;
import com.example.demo.Utils.UserSkills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user_event")
public class UserEventController {
    @Autowired
    UserEventService userEventService;

    @GetMapping("/filter")
    public List<UserEvent> filterUserEventsBySkills(@RequestParam Set<UserSkills> skills) {
        return userEventService.filterUserEventsBySkills(skills);
    }
}
