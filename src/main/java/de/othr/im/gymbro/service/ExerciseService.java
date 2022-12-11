package de.othr.im.gymbro.service;

import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.ExerciseInformation;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.repository.ExerciseInformationRepository;
import de.othr.im.gymbro.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    private final ExerciseInformationRepository exerciseInformationRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository, ExerciseInformationRepository exerciseInformationRepository) {
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
}
