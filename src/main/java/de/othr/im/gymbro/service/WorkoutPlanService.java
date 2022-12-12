package de.othr.im.gymbro.service;

import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.ExerciseSet;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.repository.ExerciseRepository;
import de.othr.im.gymbro.repository.ExerciseSetRepository;
import de.othr.im.gymbro.repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class WorkoutPlanService {

    private final ExerciseService exerciseService;
    private final EmailService emailService;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final ExerciseSetRepository exerciseSetRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public WorkoutPlanService(final ExerciseService exerciseService, final WorkoutPlanRepository workoutPlanRepository, final ExerciseSetRepository exerciseSetRepository, final EmailService emailService, final ExerciseRepository exerciseRepository) {
        this.exerciseService = exerciseService;
        this.workoutPlanRepository = workoutPlanRepository;
        this.exerciseSetRepository = exerciseSetRepository;
        this.exerciseRepository = exerciseRepository;
        this.emailService = emailService;
    }

    public List<ExerciseSet> getCompletedSets(final Exercise exercise) {
        final List<ExerciseSet> exerciseSets = exerciseSetRepository.findSetsByExercise(exercise.getId()).stream().filter(ExerciseSet::isCompleted).toList();
        final Map<Integer, List<ExerciseSet>> setsByOrder = exerciseSets.stream().collect(Collectors.groupingBy(ExerciseSet::getOrdering));
        final List<ExerciseSet> recentCompleted = setsByOrder.values().stream().map(sets -> sets.stream().max(Comparator.comparing(ExerciseSet::getCompletedAt))).filter(Optional::isPresent).map(Optional::get).toList();
        return recentCompleted;
    }

    public void deletePlan(final Long id) {
        final Optional<WorkoutPlan> workoutPlan = workoutPlanRepository.findById(id);
        if (workoutPlan.isEmpty()) return;
        exerciseService.removeExercisesFromPlan(workoutPlan.get());
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

    public void sharePlan(Long id, String email) {
        try {
            emailService.sharePlan(email, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isSetCompletedInCurrentWorkout(final WorkoutPlan plan, final ExerciseSet set) {
        return set.isCompleted() && set.getCompletedAt().after(plan.getLastStartedAt());
    }
}
