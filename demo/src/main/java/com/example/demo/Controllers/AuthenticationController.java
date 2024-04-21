package com.example.demo.Controllers;

//
import com.example.demo.Entities.User;
import com.example.demo.Repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class AuthenticationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        User user = userRepository.findByUsername(username);
        if (user == null || passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "User not found.");
            return "login";
        }
        return "redirect:/chat";
    }

//        try {
//            Optional<User> authenticatedUser= authenticationService.authenticateUser(user.getUsername(),user.getPassword());
//
//            if (authenticatedUser.isPresent()){
//                session.setAttribute("user",authenticatedUser.get());
//                return "redirect:/home";
//            }else {
//                model.addAttribute("user",user);
//                model.addAttribute("error","Wrong username or password");
//                return "/event/login";
//            }
//        }catch (IllegalArgumentException ex){
//            bindingResult.rejectValue("username","error.username",ex.getMessage());
//            return "/event/login";
//        }
    //}

//    @GetMapping("/home")
//    public String showHome(HttpSession session, Model model){
//        User user=(User) session.getAttribute("user");
//       if (user==null){
//           return "redirect:/event/login";
//       }
//       else {
//           model.addAttribute("user",user);
//           return "redirect:/home";
//       }
//    }
    @GetMapping("/logout")
    public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);//.doLogout(request, response, authentication);
        return "redirect:/login";
    }
}
