package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/unauthenticated")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmer);
    }

    @RequestMapping({"", "/", "/login"})
    public String showLoginForm(Model model) {
        model.addAttribute("userForm", new User());
        return "login/login";
    }

    @RequestMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("userForm", new User());
        return "login/signup";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ModelAndView signupUser(@Valid @ModelAttribute("userForm") final User user, final BindingResult bindingResult) {
        return loginService.signupUser(user, bindingResult);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/prelogout")
    public String showPreLogout(HttpServletRequest request, HttpServletResponse response) {
        return "login/prelogout";
    }
}

