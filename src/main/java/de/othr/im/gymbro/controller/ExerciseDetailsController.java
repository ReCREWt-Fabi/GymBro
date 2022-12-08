package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.ExerciseInformation;
import de.othr.im.gymbro.model.ExerciseSet;
import de.othr.im.gymbro.repository.ExerciseRepository;
import de.othr.im.gymbro.repository.ExerciseSetRepository;
import de.othr.im.gymbro.service.ExerciseInformationService;
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

    private final ExerciseRepository exerciseRepository;
    private final ExerciseSetRepository exerciseSetRepository;
    private final ExerciseInformationService exerciseInformationService;

    @Autowired
    public ExerciseDetailsController(ExerciseRepository exerciseRepository, ExerciseSetRepository exerciseSetRepository, ExerciseInformationService exerciseInformationService) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseSetRepository = exerciseSetRepository;
        this.exerciseInformationService = exerciseInformationService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmer);
    }

    @RequestMapping({"/{id}"})
    public String showExerciseDetails(@PathVariable("id") Long id, Model model) {
        Optional<Exercise> exercise = exerciseRepository.findById(id);
        if (exercise.isPresent()) {
            List<ExerciseSet> sets = exerciseSetRepository.findSetsByExercise(id);
            ExerciseInformation exerciseInformation = exerciseInformationService.getExerciseInformation(exercise.get().getExerciseType());
            model.addAttribute("exercise", exercise.get());
            model.addAttribute("sets", sets);
            model.addAttribute("exerciseInformation", exerciseInformation);
        } else {
            model.addAttribute("errors", "Exercise not found!");
        }

        return "exercise/exercise-details";
    }


    @RequestMapping({"/{id}/track_set"})
    public String showTrackSet(@PathVariable("id") Long exerciseId, @RequestParam(required = false) Long setId, Model model) {
        Optional<Exercise> exercise = exerciseRepository.findById(exerciseId);
        if (exercise.isPresent()) {
            Optional<ExerciseSet> set = setId != null ? exerciseSetRepository.findById(setId) : Optional.empty();
            ExerciseInformation exerciseInformation = exerciseInformationService.getExerciseInformation(exercise.get().getExerciseType());
            model.addAttribute("set", set.orElseGet(() -> ExerciseSet.createForExercise(exercise.get())));
            model.addAttribute("exercise", exercise.get());
            model.addAttribute("exerciseInformation", exerciseInformation);
        } else {
            model.addAttribute("errors", "Exercise not found!");
        }


        return "exercise/track-set";
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
        mv.setViewName(String.format("redirect:/exercise_details/%s", exerciseId));
        return mv;
    }
}
