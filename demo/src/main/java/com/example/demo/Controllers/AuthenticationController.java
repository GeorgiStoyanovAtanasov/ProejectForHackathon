package com.example.demo.Controllers;

import com.example.demo.Services.AuthenticationService;
import com.example.demo.Entities.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/event")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/login")
    public String showUserLoginForm(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpSession session, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "/login";
        }
        try {
            Optional<User> authenticatedUser= authenticationService.authenticateUser(user.getUsername(),user.getPassword());

            if (authenticatedUser.isPresent()){
                session.setAttribute("user",authenticatedUser.get());
                return "redirect:/home";
            }else {
                model.addAttribute("user",user);
                model.addAttribute("error","Wrong username or password");
                return "/event/login";
            }
        }catch (IllegalArgumentException ex){
            bindingResult.rejectValue("username","error.username",ex.getMessage());
            return "/event/login";
        }
    }

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

    @GetMapping("/out")
    public ModelAndView logoutButton(HttpSession session){
        session.removeAttribute("user");
        System.out.println("Изтрий сесията");
        return new ModelAndView("redirect:/event/home");
    }
}
