package com.gmail.etauroginskaya.springbootmodule.week4.security;

import com.gmail.etauroginskaya.springbootmodule.week4.security.model.AppUserPrincipal;
import com.gmail.etauroginskaya.week4.service.UserService;
import com.gmail.etauroginskaya.week4.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AppUserDetailsService.class);
    private final UserService userService;

    @Autowired
    public AppUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserDTO user = userService.findByUsername(username);
        if (user == null) {
            logger.info(String.format("User %s doesn't exist", username));
            throw new UsernameNotFoundException(String.format("User %s doesn't exist", username));
        }
        return new AppUserPrincipal(user);
    }
}
