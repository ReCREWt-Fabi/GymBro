package de.othr.im.gymbro.service;

import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;


@Service
public class OnboardingService {
    private final UserRepository userRepository;

    @Autowired
    public OnboardingService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ModelAndView updateUser(final User user, final String nextPage) {
        final ModelAndView mv = new ModelAndView();
        userRepository.save(user);
        mv.setViewName("redirect:/onboarding/" + nextPage);
        return mv;
    }
}
