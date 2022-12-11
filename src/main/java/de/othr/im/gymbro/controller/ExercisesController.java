package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.ExerciseInformation;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.repository.ExerciseInformationRepository;
import de.othr.im.gymbro.repository.ExerciseRepository;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = {"/exercises"})
public class ExercisesController {

    private final WorkoutPlanService workoutPlanService;

    private final ExerciseInformationRepository exerciseInformationRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExercisesController(final WorkoutPlanService workoutPlanService, final ExerciseInformationRepository exerciseInformationRepository, final ExerciseRepository exerciseRepository) {
        this.workoutPlanService = workoutPlanService;
        this.exerciseInformationRepository = exerciseInformationRepository;
        this.exerciseRepository = exerciseRepository;
    }


    @RequestMapping({"", "/"})
    public String showExerciseSelector(final Model model, @RequestParam(required = false) Long planId) {
        List<ExerciseInformation> exercises = this.exerciseInformationRepository.findAll();
        model.addAttribute("exercises", exercises);
        model.addAttribute("planId", planId);
        return "workout_plans/select_exercise";
    }

    @RequestMapping({"/{id}/submit"})
    public String showExerciseSelector(@PathVariable("id") final String exerciseId, @RequestParam(required = false) Long planId) {
        if (planId != null) {
            ExerciseInformation ei = this.exerciseInformationRepository.findById(exerciseId);
            Optional<WorkoutPlan> plan = this.workoutPlanService.getPlan(planId);
            if (ei != null && plan.isPresent()) {
                Exercise exercise = new Exercise();
                exercise.setPlan(plan.get());
                exercise.setExerciseInformation(ei);
                exerciseRepository.save(exercise);
            }
            return "redirect:/workout_plans/create?planId=" + planId;
        }
        return "redirect:/exercises";
    }
}
