package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.ExerciseSet;
import de.othr.im.gymbro.repository.ExerciseSetRepository;
import de.othr.im.gymbro.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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
    private final ExerciseService exerciseService;
    private final ExerciseSetRepository exerciseSetRepository;

    @Autowired
    public ExerciseDetailsController(ExerciseService exerciseService, ExerciseSetRepository exerciseSetRepository) {
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
        Exercise exercise = exerciseService.getExercise(exerciseId);
        if (exercise != null) {
            Optional<ExerciseSet> set = setId != null ? exerciseSetRepository.findById(setId) : Optional.empty();
            model.addAttribute("set", set.orElseGet(() -> ExerciseSet.createForExercise(exercise)));
            model.addAttribute("exercise", exercise);
        } else {
            model.addAttribute("errors", "Exercise not found!");
        }

        return "exercise/track-set";
    }

    @RequestMapping({"/{id}/delete_set/{sid}"})
    public String deleteSet(@PathVariable("id") Long exerciseId, @PathVariable("sid") Long setId) {
        exerciseSetRepository.deleteById(setId);
        return "redirect:/exercise_details/" + exerciseId;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/{id}/track_set/submit")
    public ModelAndView updateUser(@PathVariable("id") Long exerciseId, @Valid @ModelAttribute("set") final ExerciseSet set, final BindingResult bindingResult) {
        return trackSet(exerciseId, set, bindingResult);
    }

    private ModelAndView trackSet(Long exerciseId, ExerciseSet set, BindingResult result) {
        final ModelAndView mv = new ModelAndView();
        if (result.hasErrors()) {
            mv.setViewName("exercise/exercise-details");
            return mv;
        }
        set.setCompletedAt(new Date());
        exerciseSetRepository.save(set);
        mv.setViewName("redirect:/exercise_details/" + exerciseId);
        return mv;
    }
}
