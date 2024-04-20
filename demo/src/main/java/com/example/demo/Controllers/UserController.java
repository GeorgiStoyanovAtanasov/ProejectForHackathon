package com.example.demo.Controllers;

import com.example.demo.Entities.User;
import com.example.demo.Repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    LogoutHandler logoutHandler;
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            redirectAttributes.addFlashAttribute("wrongCredentials", "wrong credentials");
            return "/login";
        }
        return "redirect:/home";
    }
    @GetMapping("/logout")
    public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);//.doLogout(request, response, authentication);
        return "redirect:/login";
    }
//    @GetMapping("/home")
//    public String home(Model model) {
//
//        return "home";
//    }
//    @GetMapping("/register")
//    public String registerForm(Model model) {
//        model.addAttribute("player", new User());
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String registerPlayer(@Valid User player, BindingResult bindingResult, Model model) {
//        return playerService.registerPlayer(player, bindingResult, model);
//    }
}
