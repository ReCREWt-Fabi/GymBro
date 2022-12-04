package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.service.OnboardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = {"/onboarding"})
public class OnboardingController {
    private final OnboardingService onboardingService;

    @Autowired
    public OnboardingController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @RequestMapping({"", "/"})
    public String showOnboardingScreen(Model model) {
        return "login/onboarding";
    }

}
