package com.example.demo.Controllers;

import com.example.demo.Entities.User;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/event")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
//    @Autowired
//    LogoutHandler logoutHandler;
//--------------------------------------------------------------------------------------------
    @Autowired
    private UserServices userServices;

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute @Valid User user,  @RequestParam(value = "skills", required = false) List<String> skillNames, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "/register";
        }
        try {
            userServices.register(user, skillNames);
        }catch (RuntimeException ex){
            model.addAttribute("error", ex.getMessage());
            return "/register";
        }
        return "redirect:/login";
    }
    @GetMapping("/user/list")
    public String userList(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        List<User> users = (List<User>) userRepository.findAll();
        List<User> usersExceptForTheLogedInOne = new ArrayList<>();
        for (int i = 0; i < users.size() ; i++) {
            if(!users.get(i).equals(user)){
                usersExceptForTheLogedInOne.add(users.get(i));
            }
        }
        model.addAttribute("users", usersExceptForTheLogedInOne);
        return "user-list";
    }
}
