package com.julio.farmsys.service;

import java.util.List;
import java.util.UUID;

import com.julio.farmsys.model.User;
import com.julio.farmsys.repository.UserRepository;
import com.julio.farmsys.roles.Role;
import com.julio.farmsys.security.PasswordEncoder;
import com.julio.farmsys.util.EmailValidator;
import com.julio.farmsys.util.RegistrationRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    public String register(RegistrationRequest request) {

        User user = new User(request.getName(), request.getEmail(), request.getEmail(), request.getPassword(),
                Role.USER);

        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();

        if (userExists) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);

        return "ok";
    }

    public void save(User user) {

        user.setPassword( passwordEncoder.encode( user.getPassword() ) );

        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.getOne(id);
    }
}
