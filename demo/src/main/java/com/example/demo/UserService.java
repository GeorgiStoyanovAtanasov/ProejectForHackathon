package com.example.demo;

import com.example.demo.Entities.User;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void register(User user){
        User existingUser=userRepository.findByUsername(user.getUsername());
        if(existingUser!=null){
            throw new RuntimeException("Потребител с такова потребителско име вече съществува");
        }

        String hashedPassword= BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }
}