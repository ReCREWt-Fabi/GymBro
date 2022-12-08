package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = {"/workout_plans/create"})
public class CreatePlanController {

    private final WorkoutPlanService workoutPlanService;

    @Autowired
    public CreatePlanController(final WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @RequestMapping({"", "/"})
    public String showPlanCreator(final Model model, @RequestParam(required = false) final Long planId) {
        final WorkoutPlan plan;
        if (planId == null) {
            plan = workoutPlanService.createPlan();
        }
        else {
            plan = workoutPlanService.getPlan(planId).orElseGet(workoutPlanService::createPlan);
        }
        model.addAttribute("plan", plan);
        return "workout_plans/create";
    }

    @RequestMapping("/add_exercise")
    public String showExerciseSelector(final Model model) {
        return "redirect:/exercises";
    }
}
