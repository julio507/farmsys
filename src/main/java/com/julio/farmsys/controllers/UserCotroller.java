package com.julio.farmsys.controllers;

import java.util.ArrayList;
import java.util.List;

import com.julio.farmsys.model.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/user")
public class UserCotroller {

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>();
    }
}
