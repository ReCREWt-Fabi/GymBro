package de.othr.im.gymbro.service;

import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.ExerciseSet;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.repository.ExerciseSetRepository;
import de.othr.im.gymbro.repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class WorkoutPlanService {

    private final ExerciseService exerciseService;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final ExerciseSetRepository exerciseSetRepository;

    @Autowired
    public WorkoutPlanService(final ExerciseService exerciseService, final WorkoutPlanRepository workoutPlanRepository, final ExerciseSetRepository exerciseSetRepository) {
        this.exerciseService = exerciseService;
        this.workoutPlanRepository = workoutPlanRepository;
        this.exerciseSetRepository = exerciseSetRepository;
    }

    public List<ExerciseSet> getSets(final Exercise exercise) {
        return exerciseSetRepository.findSetsByExercise(exercise.getId());
    }

    public void deletePlan(final Long id) {
        workoutPlanRepository.deleteById(id);
    }

    public List<Exercise> getExercises(final WorkoutPlan plan) {
        return exerciseService.getExercisesForPlan(plan);
    }

    public List<WorkoutPlan> getPlans(final User user) {
        return workoutPlanRepository.findWorkoutPlansByUser(user);
    }

    public List<String> getExerciseNamesOfPlan(final WorkoutPlan plan) {
        final List<Exercise> exercises = exerciseService.getExercisesForPlan(plan);
        return exercises.stream().map(exercise -> exercise.getExerciseInformation().getName()).collect(Collectors.toList());
    }

    public String getExerciseNamesOfPlanAsString(final WorkoutPlan plan) {
        final List<String> names = getExerciseNamesOfPlan(plan);
        if (names.isEmpty()) return "No exercises in this plan yet!";
        return String.join(", ", names);
    }

    public WorkoutPlan createPlan(final User user) {
        final WorkoutPlan plan = new WorkoutPlan();
        plan.setUser(user);
        return workoutPlanRepository.save(plan);
    }

    public Optional<WorkoutPlan> getPlan(final Long planId) {
        return workoutPlanRepository.findById(planId);
    }

    public void updatePlan(final WorkoutPlan updatedPlan) {
        workoutPlanRepository.save(updatedPlan);
    }
}
