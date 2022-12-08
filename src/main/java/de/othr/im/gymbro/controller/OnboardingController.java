package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.service.OnboardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
@RequestMapping(value = "/onboarding")
public class OnboardingController {

    private final OnboardingService onboardingService;

    @Autowired
    public OnboardingController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmer);
    }

    @RequestMapping({"", "/", "/start"})
    public String showOnboardingScreen(Model model, @AuthenticationPrincipal GymBroUserDetails user) {
        model.addAttribute("userData", user.getUser());
        return "login/onboarding_start";
    }

    @RequestMapping({"/about"})
    public String showOnboardingUserScreen(Model model, @AuthenticationPrincipal GymBroUserDetails user) {
        model.addAttribute("userData", user.getUser());
        return "login/onboarding_user";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/about/submit")
    public ModelAndView updateUserGeneralData(@Valid @ModelAttribute("userData") final User formResult, final BindingResult bindingResult, @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final User currentUser = userDetails.getUser();
        currentUser.setBirthDate(formResult.getBirthDate());
        currentUser.setGender(formResult.getGender());
        if (bindingResult.hasFieldErrors("birthDate") || bindingResult.hasFieldErrors("gender")) {
            return new ModelAndView("login/onboarding_user");
        }
        return onboardingService.updateUser(currentUser, "current");
    }

    @RequestMapping("/current")
    public String showOnboardingCurrentScreen(Model model, @AuthenticationPrincipal GymBroUserDetails user) {
        model.addAttribute("userData", user.getUser());
        return "login/onboarding_current";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/current/submit")
    public ModelAndView updateUserCurrentMeasures(@Valid @ModelAttribute("userData") final User formResult, final BindingResult bindingResult, @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final User currentUser = userDetails.getUser();
        currentUser.setHeight(formResult.getHeight());
        currentUser.setWeight(formResult.getWeight());
        if (bindingResult.hasFieldErrors("height") || bindingResult.hasFieldErrors("weight")) {
            return new ModelAndView("login/onboarding_current");
        }
        return onboardingService.updateUser(currentUser, "goals");
    }

    @RequestMapping("/goals")
    public String showOnboardingGoalsScreen(Model model, @AuthenticationPrincipal GymBroUserDetails user) {
        model.addAttribute("userData", user.getUser());
        return "login/onboarding_goals";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/goals/submit")
    public ModelAndView updateUserGoals(@Valid @ModelAttribute("userData") final User formResult, final BindingResult bindingResult, @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final User currentUser = userDetails.getUser();
        currentUser.setWorkoutGoal(formResult.getWorkoutGoal());
        currentUser.setRestTime(formResult.getRestTime());
        if (bindingResult.hasFieldErrors("workoutGoal") || bindingResult.hasFieldErrors("restTime")) {
            return new ModelAndView("login/onboarding_goals");
        }
        return onboardingService.updateUser(currentUser, "finished");
    }

    @RequestMapping("/finished")
    public String showOnboardingFinishedScreen(Model model, @AuthenticationPrincipal GymBroUserDetails user) {
        model.addAttribute("userData", user.getUser());
        return "login/onboarding_finished";
    }
}
