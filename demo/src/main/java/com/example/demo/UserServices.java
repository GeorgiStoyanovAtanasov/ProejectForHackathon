package com.example.demo;

import com.example.demo.Constants.Role;
import com.example.demo.Constants.UserSkills;
import com.example.demo.Entities.User;
import com.example.demo.Entities.UserSkill;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Repositories.UserSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSkillRepository userSkillRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(User user, List<String> skills) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("Потребител с такова потребителско име вече съществува");
        }
        List<UserSkills> selectedSkills = skills.stream()
                .map(UserSkills::valueOf)
                .collect(Collectors.toList());
        user.setSkills(selectedSkills);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setRole(Role.USER);
        userRepository.save(user);
        for (int i = 0; i < selectedSkills.size(); i++) {
            userSkillRepository.save(new UserSkill(user, selectedSkills.get(i)));
        }
    }
}
