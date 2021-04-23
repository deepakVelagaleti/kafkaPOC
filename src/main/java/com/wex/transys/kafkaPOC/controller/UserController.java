package com.wex.transys.kafkaPOC.controller;

import com.wex.transys.kafkaPOC.model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class UserController
{
    @MessageMapping("/user")
    @SendTo("/topic/user")
    public User getUser(User user) {

        return new User("Hii " + user.getName());
    }
}
