package com.example.demo.Services;

import com.example.demo.Entities.User;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Repositories.UserSkillsRepository;
import com.example.demo.Utils.UserSkills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserSkillService {
    @Autowired
    UserSkillsRepository userSkillsRepository;
    @Autowired
    UserRepository userRepository;

    public List<User> filterUsersBySkills(List<UserSkills> userSkills){
        return userSkillsRepository.findByUserSkillsIn(userSkills);
    }

    public List<User> getUsersBySkill(UserSkills skill) {
        return userRepository.findByUserSkills(skill);
    }

    public List<UserSkills> getAllUserSkills() {
        return Arrays.asList(UserSkills.values());
    }
}
