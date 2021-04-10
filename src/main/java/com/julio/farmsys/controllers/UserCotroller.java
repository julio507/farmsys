package com.julio.farmsys.controllers;

import java.util.List;
import java.util.Map;

import com.julio.farmsys.model.User;
import com.julio.farmsys.service.UserService;
import com.julio.farmsys.util.RegistrationRequest;

import org.springframework.boot.json.BasicJsonParser;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/user/")
public class UserCotroller {

    private UserService userService;

    public UserCotroller(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "add")
    public String add(@RequestBody RegistrationRequest request) {
        return userService.register(request);
    }

    @GetMapping(value = "getAll")
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping(value = "update")
    public void update(@RequestBody String data) throws Exception {
        Map<String, Object> json = new BasicJsonParser().parseMap(data);

        if (json.get("name") == null || json.get("name").toString().isEmpty()) {
            throw new Exception("Valor para campo Nome invalido");
        }

        if (json.get("email") == null || json.get("email").toString().isEmpty()) {
            throw new Exception("Valor para campo E-mail invalido");
        }

        User u = new User();

        if (json.get("id") == null || json.get("id").toString().isEmpty()) {
            u.setId(0l);
        }

        else {
            u = userService.getById(NumberUtils.parseNumber(json.get("id").toString(), Long.class));
        }

        u.setName(json.get("name").toString());
        u.setEmail(json.get("email").toString());

        if (json.get("password") != null) {
            u.setPassword(json.get("password").toString());
        }

        else if (u.getId() == 0) {
            throw new Exception("Valor para campo Senha invalido");
        }

        u.setEnabled(Boolean.valueOf(json.get("enabled").toString()));
        u.setUsername(u.getEmail());

        userService.save(u);
    }
}
