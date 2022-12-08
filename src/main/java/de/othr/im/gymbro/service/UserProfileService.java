package de.othr.im.gymbro.service;

import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

@Service
public class UserProfileService {
    private final UserRepository userRepository;

    @Autowired
    public UserProfileService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ModelAndView updateUser(final User user, final BindingResult result) {
        final ModelAndView mv = new ModelAndView();
        if (result.hasErrors()) {
            mv.setViewName("user-profile/edit-user-profile");
            return mv;
        }
        userRepository.save(user);
        mv.setViewName("redirect:/user_profile");
        return mv;
    }
}
