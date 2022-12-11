package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


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
    public ModelAndView deleteWorkoutPlan(final @RequestParam Long planId) {
        workoutPlanService.deletePlan(planId);
        return new ModelAndView("redirect:/workout_plans");
    }

    @RequestMapping({"/{id}/share"})
    public String showShareWorkoutPlan(final Model model, final @PathVariable Long id) {
        model.addAttribute("planId", id);
        model.addAttribute("userData", new User());
        return "workout_plans/share";
    }

    @RequestMapping({"/{id}/follow"})
    public String followWorkoutPlan(final Model model, final @PathVariable Long id, @AuthenticationPrincipal GymBroUserDetails userDetails) {
        Optional<WorkoutPlan> plan = workoutPlanService.getPlan(id);
        if (plan.isPresent()) {
            User user = userDetails.getUser();
            plan.get().getFollowers().add(user);
            workoutPlanService.updatePlan(plan.get());
            model.addAttribute("result", "You are now following this plan!");
            model.addAttribute("plan", plan.get());
        } else {
            model.addAttribute("result", "This plan does not exist!");
        }
        return "workout_plans/followed";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/share/submit")
    public ModelAndView shareWorkoutPlan(final @PathVariable Long id, final @ModelAttribute("userData") User userData, final BindingResult result) {
        final ModelAndView mv = new ModelAndView();
        if (result.hasFieldErrors("email")) {
            mv.setViewName("redirect:/workout_plans/" + id + "/share");
            return mv;
        }
        workoutPlanService.sharePlan(id, userData.getEmail());
        mv.setViewName("redirect:/workout_plans");
        return mv;
    }

}
