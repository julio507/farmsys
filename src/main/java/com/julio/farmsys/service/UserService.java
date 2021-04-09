package com.julio.farmsys.service;

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
    private final EmailValidator emailValidator;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, EmailValidator emailValidator, BCryptPasswordEncoder passwordEncoder ) {
        this.userRepository = userRepository;
        this.emailValidator = emailValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail( username ).orElseThrow( () -> new UsernameNotFoundException( "User Not Found" ) );
    }

    public String register( RegistrationRequest request){
        boolean isValid = emailValidator.test( request.getEmail() );
        
        if( !isValid )
        {
            throw new IllegalStateException( "Validation error" );
        }

        User user = new User( 
            request.getFirstName(),
            request.getEmail(),
            request.getEmail(),
            request.getPassword(),
            false,
            true,
            Role.USER );

        boolean userExists = userRepository.findByEmail( user.getEmail() ).isPresent();

        if (userExists) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = passwordEncoder.encode( user.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

ConfirmationToken confirmationToken = new ConfirmationToken(
        token,
        LocalDateTime.now(),
        LocalDateTime.now().plusMinutes(15),
        appUser
);

confirmationTokenService.saveConfirmationToken(
        confirmationToken);

//        TODO: SEND EMAIL

return token;
    }
}
