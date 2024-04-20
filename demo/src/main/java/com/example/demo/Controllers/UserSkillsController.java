package com.example.demo.Controllers;

import com.example.demo.Services.UserSkillService;
import com.example.demo.Utils.UserSkills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user_skills")
public class UserSkillsController {
    @Autowired
    UserSkillService userSkillService;

    @GetMapping("/all")
    public List<UserSkills> getAllUserSkills() {
        return userSkillService.getAllUserSkills();
    }
}
