package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/followed"})
public class FollowedController {

    private final WorkoutPlanService workoutPlanService;

    @Autowired
    public FollowedController(final WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @RequestMapping({"", "/"})
    public String showWorkoutPlans(final Model model, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        model.addAttribute("plans", userDetails.getUser().getFollowedPlans());
        model.addAttribute("service", workoutPlanService);
        return "workout_plans/followed";
    }

}
