package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.model.WorkoutPlanTemplates;
import de.othr.im.gymbro.service.ExerciseService;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import java.util.Optional;


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
    public String showPlanDetails(final Model model, @RequestParam final Long planId,
                                  final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final WorkoutPlan plan = workoutPlanService.getPlan(planId).orElseGet(() -> workoutPlanService.createPlan(userDetails.getUser()));
        if(Objects.equals(plan.getUser().getId(), userDetails.getUser().getId())) {
            model.addAttribute("read_only", false);
        } else {
            model.addAttribute("read_only", true);
        }
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("plan", plan);
        model.addAttribute("exercises", workoutPlanService.getExercises(plan));
        model.addAttribute("service", workoutPlanService);
        model.addAttribute("planTemplates", WorkoutPlanTemplates.values());
        return "workout_plans/details";
    }

    @RequestMapping("/apply_template")
    public String templateSelected(final @RequestParam Long planId, final @RequestParam WorkoutPlanTemplates template, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        for (final String exerciseId : template.getExercises()) {
            exerciseService.createExercise(exerciseId, planId, userDetails.getUser());
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

    @RequestMapping({"/unfollow"})
    public String unfollowWorkoutPlan(final Model model, final @RequestParam Long planId,
                                    final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        // TODO: Do something with result, here and in follow
        Optional<WorkoutPlan> plan = workoutPlanService.getPlan(planId);
        if (plan.isPresent()) {
            User user = userDetails.getUser();
            if (plan.get().getFollowers().contains(user)) {
                plan.get().getFollowers().remove(user);
                user.removeFollowedPlan(plan.get());
                workoutPlanService.updatePlan(plan.get());
                model.addAttribute("result", "Fuck this!");
            } else {
                model.addAttribute("result", "You are not following this plan!");
            }
        } else {
            model.addAttribute("result", "This plan does not exist!");
        }
        return "redirect:/followed";
    }

}
