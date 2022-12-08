package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = {"/workout_plans"})
public class WorkoutPlansController {

    @RequestMapping({"", "/"})
    public String showWorkoutPlans(final Model model, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        model.addAttribute("plans", userDetails.getUser().getPlans());
        return "workout_plans/overview";
    }

}
