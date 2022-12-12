package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.service.ExerciseService;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;


@Controller
@RequestMapping(value = {"/workout"})
public class WorkoutController {
    private final WorkoutPlanService workoutPlanService;

    @Autowired
    public WorkoutController(final WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @RequestMapping({"", "/"})
    public String showWorkoutScreen(final Model model, @RequestParam final Long planId, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final WorkoutPlan plan = workoutPlanService.getPlan(planId).orElseGet(() -> workoutPlanService.createPlan(userDetails.getUser()));
        if (plan.getLastCompletedAt() == null || plan.getLastCompletedAt().after(plan.getLastStartedAt())) {
            plan.setLastStartedAt(new Date());
            workoutPlanService.updatePlan(plan);
        }
        model.addAttribute("plan", plan);
        model.addAttribute("exercises", workoutPlanService.getExercises(plan));
        model.addAttribute("service", workoutPlanService);
        return "workout_plans/workout";
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/complete"})
    public String completeWorkout(@RequestParam final Long planId) {
        final WorkoutPlan plan = workoutPlanService.getPlan(planId).orElseThrow();
        if (plan.getLastCompletedAt() == null || plan.getLastCompletedAt().before(plan.getLastStartedAt())) {
            plan.setLastCompletedAt(new Date());
            workoutPlanService.updatePlan(plan);
        }
        return "redirect:/workout_plans";
    }
}
