package com.example.demo.Services;

import com.example.demo.Entities.User;
import com.example.demo.Entities.UserEvent;
import com.example.demo.Repositories.EventRepository;
import com.example.demo.Repositories.UserEventRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Utils.UserSkills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private UserEventRepository userEventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;

    public List<User> getAllUsers(){
        return (List<User>) userRepository.findAll();
    }

//    public List<UserEvent> getUsersBySkills(UserSkills skills) {
//        if (skills == null) {
//            return (List<UserEvent>) userEventRepository.findAll();
//        } else {
//            return userEventRepository.getUsersBySkills(Collections.singleton(skills));
//        }
//    }
}
