package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.ExerciseSet;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.repository.ExerciseSetRepository;
import de.othr.im.gymbro.service.ExerciseService;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = {"/exercise_details"})
public class ExerciseDetailsController {
    private final WorkoutPlanService workoutPlanService;
    private final ExerciseService exerciseService;
    private final ExerciseSetRepository exerciseSetRepository;

    @Autowired
    public ExerciseDetailsController(WorkoutPlanService workoutPlanService, ExerciseService exerciseService, ExerciseSetRepository exerciseSetRepository) {
        this.workoutPlanService = workoutPlanService;
        this.exerciseService = exerciseService;
        this.exerciseSetRepository = exerciseSetRepository;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmer);
    }

    @RequestMapping({"/{id}"})
    public String showExerciseDetails(@PathVariable("id") Long id, Model model) {
        Exercise exercise = exerciseService.getExercise(id);
        if (exercise != null) {
            List<ExerciseSet> sets = exerciseSetRepository.findSetsByExercise(id);
            model.addAttribute("exercise", exercise);
            model.addAttribute("sets", sets);
        } else {
            model.addAttribute("errors", "Exercise not found!");
        }

        return "exercise/exercise-details";
    }


    @RequestMapping({"/{id}/track_set"})
    public String showTrackSet(@PathVariable("id") Long exerciseId, @RequestParam(required = false) Long setId, Model model) {
        final Exercise exercise = exerciseService.getExercise(exerciseId);
        if (exercise != null) {
            final Optional<ExerciseSet> maybeSet = setId != null ? exerciseSetRepository.findById(setId) : Optional.empty();
            final ExerciseSet set = maybeSet.orElseGet(() -> {
                        final List<ExerciseSet> completedSets = workoutPlanService.getCompletedSets(exercise);
                        final int nextIndex = completedSets.size() + 1;
                        return ExerciseSet.createForExercise(exercise, completedSets, nextIndex);
                    }
            );
            model.addAttribute("set", set);
            model.addAttribute("exercise", exercise);
        } else {
            model.addAttribute("errors", "Exercise not found!");
        }

        return "exercise/track-set";
    }

    @RequestMapping({"/{id}/delete_set/{sid}"})
    public String deleteSet(@PathVariable("id") Long exerciseId, @PathVariable("sid") Long setId) {
        exerciseSetRepository.deleteById(setId);
        final Exercise exercise = exerciseService.getExercise(exerciseId);
        return "redirect:/workout?planId=" + exercise.getPlan().getId();
    }


    @RequestMapping(method = RequestMethod.POST, value = "/{id}/track_set/submit")
    public ModelAndView updateUser(@PathVariable("id") Long exerciseId,
                                   @Valid @ModelAttribute("set") final ExerciseSet set,
                                   final BindingResult bindingResult,
                                   @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final ModelAndView mv = new ModelAndView();
        if (bindingResult.hasFieldErrors("weight") || bindingResult.hasFieldErrors("reps")) {
            mv.setViewName("redirect:/exercise_details/" + exerciseId + "/track_set");
            return mv;
        }
        set.setCompletedAt(new Date());
        set.setUser(userDetails.getUser());
        exerciseSetRepository.save(set);
        mv.setViewName("redirect:/workout?planId=" + set.getExercise().getPlan().getId());
        return mv;
    }
}
