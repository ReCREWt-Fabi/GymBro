package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = {"/workout_plans"})
public class WorkoutPlansController {
    private final WorkoutPlanService workoutPlanService;

    @Autowired
    public WorkoutPlansController(final WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @RequestMapping({"", "/"})
    public String showWorkoutPlans(final Model model, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        model.addAttribute("plans", workoutPlanService.getPlans(userDetails.getUser()));
        model.addAttribute("service", workoutPlanService);
        return "workout_plans/overview";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ModelAndView updateUser(final @RequestParam Long planId) {
        workoutPlanService.deletePlan(planId);
        return new ModelAndView("redirect:/workout_plans");
    }
}
