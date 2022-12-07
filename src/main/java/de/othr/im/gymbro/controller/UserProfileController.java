package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.service.UserProfileService;
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
@RequestMapping(value = {"/user_profile"})
public class UserProfileController {

    private final UserProfileService userProfileService;


    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmer);
    }

    @RequestMapping({"", "/"})
    public String showUserProfile(Model model, @AuthenticationPrincipal GymBroUserDetails user) {
        model.addAttribute("userData", user.getUser());
        return "user-profile/user-profile";
    }

    @RequestMapping({"/edit"})
    public String editUserProfile(Model model, @AuthenticationPrincipal GymBroUserDetails user) {
        model.addAttribute("userForm", user.getUser());
        return "user-profile/edit-user-profile";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/edit/submit")
    public ModelAndView updateUser(@Valid @ModelAttribute("userForm") final User user, final BindingResult bindingResult, @AuthenticationPrincipal GymBroUserDetails userDetails) {
        userDetails.setUser(user);
        return userProfileService.updateUser(user, bindingResult);
    }
}
