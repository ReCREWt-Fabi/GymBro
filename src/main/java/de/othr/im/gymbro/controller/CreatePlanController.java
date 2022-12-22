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
    public String showPlanCreator(Model model, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final WorkoutPlan plan = workoutPlanService.createPlan(userDetails.getUser());
        model.addAttribute("plan", plan);
        return "workout_plans/create";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit")
    public String savePlan(@Valid @ModelAttribute("plan") final WorkoutPlan formResult, final @RequestParam Long planId,
                           BindingResult bindingResult, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            workoutPlanService.deletePlan(planId);
            return "redirect:/workout_plans/create";
        } else {
            final WorkoutPlan plan = workoutPlanService.getPlan(planId).get();
            plan.setName(formResult.getName());
            workoutPlanService.updatePlan(plan);
            return "redirect:/workout_plans/details?planId=" + planId;
        }
    }

}
