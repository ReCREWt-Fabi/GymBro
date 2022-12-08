package de.othr.im.gymbro.service;

import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.repository.ExerciseRepository;
import de.othr.im.gymbro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Service
public class WorkoutPlanService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public WorkoutPlanService(final ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }


    public Optional<Exercise> getExercise(final Long id, final User user) {
        final Exercise res = user.getPlans().stream().flatMap(plan -> plan.getExercises().stream()).filter(exercise -> exercise.getId().equals(id)).findFirst().orElse(null);
        return Optional.ofNullable(res);
    }

    public ModelAndView saveExercise(final Exercise formResult, final BindingResult result, final String targetView) {
        final ModelAndView mv = new ModelAndView();
        if (result.hasErrors()) {
            mv.setViewName("workout_plans/edit_exercise");
            return mv;
        }
        exerciseRepository.save(formResult);
        mv.setViewName("redirect:" +  targetView);
        return mv;
    }
}
