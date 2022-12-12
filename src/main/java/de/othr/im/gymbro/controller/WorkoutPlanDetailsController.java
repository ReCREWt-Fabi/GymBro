package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.model.WorkoutPlanTemplates;
import de.othr.im.gymbro.service.ExerciseService;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = {"/workout_plans/details"})
public class WorkoutPlanDetailsController {
    private final WorkoutPlanService workoutPlanService;
    private final ExerciseService exerciseService;

    @Autowired
    public WorkoutPlanDetailsController(final WorkoutPlanService workoutPlanService, final ExerciseService exerciseService) {
        this.workoutPlanService = workoutPlanService;
        this.exerciseService = exerciseService;
    }

    @RequestMapping({"", "/"})
    public String showPlanDetails(final Model model, @RequestParam final Long planId, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final WorkoutPlan plan = workoutPlanService.getPlan(planId).orElseGet(() -> workoutPlanService.createPlan(userDetails.getUser()));
        model.addAttribute("plan", plan);
        model.addAttribute("exercises", workoutPlanService.getExercises(plan));
        model.addAttribute("service", workoutPlanService);
        model.addAttribute("planTemplates", WorkoutPlanTemplates.values());
        return "workout_plans/details";
    }

    @RequestMapping("/apply_template")
    public String templateSelected(final @RequestParam Long planId, final @RequestParam WorkoutPlanTemplates template, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        for (final String type : template.getExercises()) {
            exerciseService.createExercise(type, planId, userDetails.getUser());
        }
        return "redirect:/workout_plans/details?planId=" + planId;
    }

    @RequestMapping("/add_exercise")
    public String showExerciseSelector(final @RequestParam(required = false) Long planId) {
        return "redirect:/exercises?planId=" + planId;
    }

    @RequestMapping(value = "/delete_exercise")
    public ModelAndView deleteExercise(final @RequestParam Long exerciseId, final @RequestParam Long planId) {
        exerciseService.deleteExercise(exerciseId);
        return new ModelAndView("redirect:/workout_plans/details?planId=" + planId);
    }
}
