package com.example.demo.Services;

import com.example.demo.Entities.UserEvent;
import com.example.demo.Repositories.UserEventRepository;
import com.example.demo.Repositories.UserSkillsRepository;
import com.example.demo.Utils.UserSkills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserEventService {
    @Autowired
    UserEventRepository userEventRepository;

    public List<UserEvent> filterUserEventsBySkills(Set<UserSkills> desiredSkills) {
        return userEventRepository.findByUser_UserSkillsIn(desiredSkills);
    }
}
