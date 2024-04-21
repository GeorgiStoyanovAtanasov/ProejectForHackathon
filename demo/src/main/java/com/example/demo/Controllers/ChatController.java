package com.example.demo.Controllers;

import com.example.demo.Entities.Message;
import com.example.demo.Entities.User;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.ChatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatService chatService;

    @GetMapping("/{receiverUsername}")
    public String chatWithUser(@PathVariable String receiverUsername, Model model, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderUsername = authentication.getName();

        User sender = userRepository.findByUsername(senderUsername);
        User receiver = userRepository.findByUsername(receiverUsername);

            List<Message> messages = chatService.getMessagesBetweenUsers(sender, receiver);

            model.addAttribute("messages", messages);
            model.addAttribute("receiverUsername", receiverUsername);

            return "chat";
        }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String content, @RequestParam String receiverUsername, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderUsername = authentication.getName();

        User sender = userRepository.findByUsername(senderUsername);
        User receiver = userRepository.findByUsername(receiverUsername);
        if(sender.equals(receiver)){
            return "redirect:/home";
        }
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);

        chatService.saveMessage(message);

        return "redirect:/chat/" + receiverUsername;
    }
    @GetMapping("/{receiverUsername}/messages")
    @ResponseBody
    public List<Message> getMessages(@PathVariable String receiverUsername) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderUsername = authentication.getName();

        User sender = userRepository.findByUsername(senderUsername);
        User receiver = userRepository.findByUsername(receiverUsername);

        return chatService.getMessagesBetweenUsers(sender, receiver);
    }

}

