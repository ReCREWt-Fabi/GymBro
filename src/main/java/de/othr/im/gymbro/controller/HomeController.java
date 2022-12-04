package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = {"/home"})
public class HomeController {
    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @RequestMapping({"", "/"})
    public String showHomeScreen(Model model) {
        return "home";
    }

}
