package com.julio.farmsys.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PagesController {
 
    @GetMapping( "login" )
    public String login( Model model ){
        return "login";
    }
    
    @GetMapping( "users" )
    public String users(Model model) {
        return "users";
    }

    @GetMapping( "animals" )
    public String animals(Model model){
        return "animals";
    }

    @GetMapping( "history" )
    public String history( Model model) {
        return "history";
    }
    
}
