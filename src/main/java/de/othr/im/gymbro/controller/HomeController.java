package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = {"/home"})
public class HomeController {

    private final WorkoutPlanService workoutPlanService;

    @Autowired
    public HomeController(WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @RequestMapping({"", "/"})
    public String showHomeScreen(final Model model, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final WorkoutPlan currentPlan = workoutPlanService.getPlans(userDetails.getUser()).stream()
                .filter(plan -> plan.isRunning())
                .max(Comparator.comparing(WorkoutPlan::getLastStartedAt)).orElse(null);
        model.addAttribute("currentPlan", currentPlan);
        model.addAttribute("service", workoutPlanService);
        return "home";
    }

}
