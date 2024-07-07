package com.example.providers.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    /* Load user by username */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return User.builder()
                .username(user.username())
                .password(user.password())
                .roles(user.roles().toArray(new String[0]))
                .build();
    }

    public static record Usuario(String username, String password, Set<String> roles) {}

    /* Find user by username */
    public static Usuario findUserByUsername(String username) {
        String encryptedPassword = "$2a$10$wBOJhj7Lh3xtSpzzEEJuD.UVT6H85ZFRXMoGNon/Ki.mGugwNGq4y"; // User's encrypted password

        Usuario exampleUser = new Usuario(
                "castro",
                encryptedPassword,
                Set.of("USER") // Example user role
        );

        List<Usuario> users = List.of(exampleUser); // List of example users

        return users.stream()
                .filter(user -> user.username().equals(username))
                .findFirst()
                .orElse(null);
    }
}