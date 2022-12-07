package de.othr.im.gymbro.service;

import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ModelAndView signupUser(final User user, final BindingResult result) {
        final ModelAndView mv = new ModelAndView();
        if (result.hasErrors()) {
            mv.setViewName("login/signup");
            return mv;
        }
        Optional<User> existingUser = userRepository.findUserByEmail(user.getEmail().trim());
        if (existingUser.isPresent()) {
            result.rejectValue("email", "error.user", "There is already a user registered with the email provided");
            mv.setViewName("login/signup");
            return mv;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        mv.setViewName("redirect:/onboarding");
        return mv;
    }
}
