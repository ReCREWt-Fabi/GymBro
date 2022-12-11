package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping(value = {"/workout_plans/create"})
public class CreatePlanController {

    private final WorkoutPlanService workoutPlanService;

    @Autowired
    public CreatePlanController(final WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @RequestMapping({"", "/"})
    public String showPlanCreator(final Model model, @RequestParam(required = false) final Long planId, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final WorkoutPlan plan;
        final boolean initialized;
        if (planId == null) {
            plan = workoutPlanService.createPlan(userDetails.getUser());
            initialized = false;
        } else {
            plan = workoutPlanService.getPlan(planId).orElseGet(() -> workoutPlanService.createPlan(userDetails.getUser()));
            initialized = true;
        }
        model.addAttribute("isInitialized", initialized);
        model.addAttribute("plan", plan);
        model.addAttribute("exercises", workoutPlanService.getExercises(plan));
        model.addAttribute("service", workoutPlanService);
        return "workout_plans/create";
    }

    @RequestMapping("/add_exercise")
    public String showExerciseSelector(final @RequestParam(required = false) Long planId) {
        return "redirect:/exercises?planId=" + planId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit")
    public ModelAndView updateUser(@Valid @ModelAttribute("plan") final WorkoutPlan formResult, final @RequestParam Long planId, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return new ModelAndView("redirect:/workout_plans/create?planId=" + planId);
        final Optional<WorkoutPlan> plan = workoutPlanService.getPlan(planId);
        if (plan.isPresent()) {
            final WorkoutPlan updatedPlan = plan.get();
            updatedPlan.setName(formResult.getName());
            workoutPlanService.updatePlan(updatedPlan);
        }
        return new ModelAndView("redirect:/workout_plans/create?planId=" + planId);
    }
}
