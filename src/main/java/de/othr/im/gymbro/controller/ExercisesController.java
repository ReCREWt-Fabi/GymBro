package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.*;
import de.othr.im.gymbro.repository.ExerciseInformationRepository;
import de.othr.im.gymbro.repository.ExerciseRepository;
import de.othr.im.gymbro.service.ExerciseService;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Locale;
import java.util.Optional;


@Controller
@RequestMapping(value = {"/exercises"})
public class ExercisesController {

    private final ExerciseService exerciseService;

    private final ExerciseInformationRepository exerciseInformationRepository;

    @Autowired
    public ExercisesController(final ExerciseService exerciseService, final ExerciseInformationRepository exerciseInformationRepository, final ExerciseRepository exerciseRepository) {
        this.exerciseService = exerciseService;
        this.exerciseInformationRepository = exerciseInformationRepository;
    }


    @RequestMapping({"", "/"})
    public String showExerciseSelector(final Model model, @RequestParam(required = false) Long planId) {
        final List<ExerciseInformation> exercises = this.exerciseInformationRepository.findAll();
        model.addAttribute("exercises", exercises);
        model.addAttribute("planId", planId);
        model.addAttribute("filter", new Filter());
        return "workout_plans/select_exercise";
    }

    @RequestMapping({"/{id}/submit"})
    public String showExerciseSelector(@PathVariable("id") final String exerciseId, @RequestParam(required = false) Long planId, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        if (planId != null) {
            this.exerciseService.createExercise(exerciseId, planId, userDetails.getUser());
            return "redirect:/workout_plans/details?planId=" + planId;
        }
        return "redirect:/exercises";
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/filter"})
    public ModelAndView filter(@ModelAttribute("filter") final Filter filter, @RequestParam(required = false) Long planId) {
        final List<ExerciseInformation> exercises = this.exerciseInformationRepository.findAll();
        final ModelAndView model = new ModelAndView("workout_plans/select_exercise");
        model.addObject("exercises", exercises.stream().filter(e -> e.getName().toLowerCase(Locale.ROOT).contains(filter.getQuery().toLowerCase(Locale.ROOT))).toList());
        model.addObject("planId", planId);
        model.addObject("filter", filter);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/reset_filter"})
    public ModelAndView filter(@RequestParam(required = false) Long planId) {
        return filter(new Filter(), planId);
    }
}
