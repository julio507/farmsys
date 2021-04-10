package com.julio.farmsys.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {
    
    @GetMapping( "users" )
    public String users(Model model) {
        return "users";
    }

    @GetMapping( "animals" )
    public String animals(Model model){
        return "animals";
    }
}
