package com.julio.farmsys.controllers;

import com.julio.farmsys.service.UserService;
import com.julio.farmsys.util.RegistrationRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/user/")
public class UserCotroller {

    private UserService userService;

    public UserCotroller( UserService userService )
    {
        this.userService = userService;
    }

    @PostMapping( value = "add" )
    public String add( @RequestBody RegistrationRequest request ){
        return userService.register( request );
    }
}
