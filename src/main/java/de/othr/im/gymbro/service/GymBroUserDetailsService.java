package de.othr.im.gymbro.service;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GymBroUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public GymBroUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findUserByEmail(username.trim()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new GymBroUserDetails(user);
    }

}
