//package com.example.demo;
//
//import com.example.demo.Entities.User;
//import com.example.demo.Repositories.UserRepository;
//import jakarta.servlet.http.HttpSession;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import java.util.Optional;
//
//@Service
//public class AuthenticationService {
//    @Autowired
//    private UserRepository userRepository;
//
//    public Optional<User> authenticateUser(String username, String password){
//        User user=userRepository.findByUsername(username);
//        if (user==null){
//            return Optional.empty();
//        }
//        if (new BCryptPasswordEncoder().matches(password,user.getPassword())){
//            return Optional.of(user);
//        }
//        return Optional.empty();
//    }
//
//    public Optional<User>getUserByUsername(String username){
//        return Optional.ofNullable(userRepository.findByUsername(username));
//    }
//
//}
