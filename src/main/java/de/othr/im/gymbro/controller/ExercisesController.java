package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
@RequestMapping(value = {"/exercises"})
public class ExercisesController {

    private final WorkoutPlanService workoutPlanService;

    @Autowired
    public ExercisesController(final WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }


    @RequestMapping({"", "/"})
    public String showExerciseSelector(final Model model, @RequestParam(required = false) Long planId) {
        final Optional<WorkoutPlan> plan = workoutPlanService.getPlan(planId);
        model.addAttribute("plan", plan.orElse(null));
        return "workout_plans/select_exercise";
    }
}
