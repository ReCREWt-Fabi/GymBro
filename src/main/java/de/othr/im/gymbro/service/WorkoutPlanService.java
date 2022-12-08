package de.othr.im.gymbro.service;

import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.repository.ExerciseRepository;
import de.othr.im.gymbro.repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class WorkoutPlanService {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutPlanRepository workoutPlanRepository;

    @Autowired
    public WorkoutPlanService(final ExerciseRepository exerciseRepository, final WorkoutPlanRepository workoutPlanRepository) {
        this.exerciseRepository = exerciseRepository;
        this.workoutPlanRepository = workoutPlanRepository;
    }


    public Optional<Exercise> getExercise(final Long id, final User user) {
        final Exercise res = user.getPlans().stream().flatMap(plan -> plan.getExercises().stream()).filter(exercise -> exercise.getId().equals(id)).findFirst().orElse(null);
        return Optional.ofNullable(res);
    }

    public WorkoutPlan createPlan() {
        return workoutPlanRepository.save(new WorkoutPlan());
    }

    public Optional<WorkoutPlan> getPlan(final Long planId) {
        return workoutPlanRepository.findById(planId);
    }
}
