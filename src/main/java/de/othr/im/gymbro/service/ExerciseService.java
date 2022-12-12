package de.othr.im.gymbro.service;

import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.ExerciseInformation;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.repository.ExerciseInformationRepository;
import de.othr.im.gymbro.repository.ExerciseRepository;
import de.othr.im.gymbro.repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private final WorkoutPlanRepository workoutPlanRepository;

    private final ExerciseRepository exerciseRepository;

    private final ExerciseInformationRepository exerciseInformationRepository;

    @Autowired
    public ExerciseService(WorkoutPlanRepository workoutPlanRepository, ExerciseRepository exerciseRepository, ExerciseInformationRepository exerciseInformationRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
        this.exerciseRepository = exerciseRepository;
        this.exerciseInformationRepository = exerciseInformationRepository;
    }

    public List<Exercise> getExercisesForPlan(final WorkoutPlan plan) {
        final List<Exercise> exercises = exerciseRepository.findByPlan(plan);
        for (final Exercise exercise : exercises) {
            final ExerciseInformation info = exerciseInformationRepository.findById(exercise.getExerciseType());
            exercise.setExerciseInformation(info);
        }

        return exercises;
    }

    public Exercise getExercise(final Long id) {
        final Optional<Exercise> exercise = exerciseRepository.findById(id);
        if (exercise.isEmpty()) {
            return null;
        }
        final ExerciseInformation info = exerciseInformationRepository.findById(exercise.get().getExerciseType());
        exercise.get().setExerciseInformation(info);

        return exercise.get();
    }

    public void createExercise(final String exerciseId, final Long planId, final User user) {
        final ExerciseInformation ei = this.exerciseInformationRepository.findById(exerciseId);
        final Optional<WorkoutPlan> plan = this.workoutPlanRepository.findById(planId);
        if (ei != null && plan.isPresent()) {
            Exercise exercise = new Exercise();
            exercise.setPlan(plan.get());
            exercise.setUser(user);
            exercise.setExerciseInformation(ei);
            exerciseRepository.save(exercise);
        }
    }

    public void deleteExercise(final Long id) {
        this.exerciseRepository.deleteById(id);
    }


    public void removeExercisesFromPlan(final WorkoutPlan plan) {
        this.exerciseRepository.removeExercisesFromPlan(plan);
    }
}
