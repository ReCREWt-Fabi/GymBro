package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.model.WorkoutPlanTemplates;
import de.othr.im.gymbro.service.ExerciseService;
import de.othr.im.gymbro.service.UserProfileService;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import java.util.Optional;


@Controller
@RequestMapping(value = {"/workout_plans/details"})
public class WorkoutPlanDetailsController {
    private final WorkoutPlanService workoutPlanService;
    private final UserProfileService userProfileService;
    private final ExerciseService exerciseService;

    @Autowired
    public WorkoutPlanDetailsController(final WorkoutPlanService workoutPlanService,
                                        final UserProfileService userProfileService,
                                        final ExerciseService exerciseService) {
        this.workoutPlanService = workoutPlanService;
        this.userProfileService = userProfileService;
        this.exerciseService = exerciseService;
    }

    @RequestMapping({"", "/"})
    public String showPlanDetails(final Model model, @RequestParam final Long planId,
                                  final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final WorkoutPlan plan = workoutPlanService.getPlan(planId).orElseGet(() -> workoutPlanService.createPlan(userDetails.getUser()));
        boolean has_access = false;
        boolean read_only = true;
        if(Objects.equals(plan.getUser().getId(), userDetails.getUser().getId())) {
            read_only = false;
            has_access = true;
        } else if (plan.isFollower(userDetails.getUser())) {
            has_access = true;
        }
        model.addAttribute("has_access", has_access);
        model.addAttribute("read_only", read_only);
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
            if (plan.get().isFollower(user)) {
                if (plan.get().removeFollower(user) && user.removeFollowedPlan(plan.get())) {
                    if (plan.get().isRunning(user)) {
                        plan.get().removeStartedBy(user);
                    }
                    workoutPlanService.updatePlan(plan.get());
                    userProfileService.repoUpdateUser(user);
                    model.addAttribute("result", "Unfollowed Plan");
                } else {
                    model.addAttribute("result", "Error unfollowing plan");
                }
            } else {
                model.addAttribute("result", "You are not following this plan!");

            }
        } else {
            model.addAttribute("result", "This plan does not exist!");
        }
        model.addAttribute("plans", userDetails.getUser().getFollowedPlans());
        model.addAttribute("service", workoutPlanService);
        return "workout_plans/followed";
    }

}
