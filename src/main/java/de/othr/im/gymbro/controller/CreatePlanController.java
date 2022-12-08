package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.ExerciseSet;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.repository.ExerciseInformationRepositoryImpl;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping(value = {"/workout_plans/create"})
public class CreatePlanController {

    private final WorkoutPlanService workoutPlanService;
    private WorkoutPlan currentPlan;

    @Autowired
    public CreatePlanController(WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @RequestMapping({"", "/"})
    public String showPlanCreator(final Model model) {
        if (currentPlan == null) {
            currentPlan = new WorkoutPlan();
        }
        model.addAttribute("plan", currentPlan);
        return "workout_plans/create";
    }

    @RequestMapping("/add_exercise")
    public String showExerciseCreator(final Model model) {
        final Exercise exercise = new Exercise();
        exercise.setExerciseType("Bicep Curl");
        exercise.setExerciseInformation(ExerciseInformationRepositoryImpl.DUMMY_VALUES.get("Bicep Curl"));
        exercise.setPlan(this.currentPlan);
        exercise.getSets().add(new ExerciseSet(1, 0, 0, exercise));
        model.addAttribute("exercise", exercise);
        return "workout_plans/edit_exercise";
    }

    @RequestMapping("/edit_exercise/{id}")
    public String showExerciseEditor(final Model model, final @PathVariable Long id, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final Optional<Exercise> exercise = workoutPlanService.getExercise(id, userDetails.getUser());
        model.addAttribute("exercise", exercise.orElseThrow());
        return "workout_plans/edit_exercise";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/edit_exercise/submit")
    public ModelAndView saveExercise(@Valid @ModelAttribute("exercise") final Exercise formResult, final BindingResult bindingResult) {
        return workoutPlanService.saveExercise(formResult, bindingResult, "workout_plans/create");
    }
}
