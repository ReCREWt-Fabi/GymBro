package de.othr.im.gymbro.service;

import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.ExerciseCategory;
import de.othr.im.gymbro.model.ExerciseInformation;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.repository.ExerciseInformationRepository;
import de.othr.im.gymbro.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ExerciseInformationService {

    private final ExerciseRepository exerciseRepository;

    private final ExerciseInformationRepository exerciseInformationRepository;

    @Autowired
    public ExerciseInformationService(ExerciseRepository exerciseRepository, ExerciseInformationRepository exerciseInformationRepository) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseInformationRepository = exerciseInformationRepository;
    }

    public ExerciseInformation getExerciseInformation(String exerciseType) {
        ExerciseInformation exerciseInformation = new ExerciseInformation();
        exerciseInformation.setName("Dummy Exercise");
        exerciseInformation.setDescription("Dummy Description");
        exerciseInformation.setCategories(Arrays.stream(new ExerciseCategory[]{ExerciseCategory.CHEST, ExerciseCategory.BACK}).toList());
        return exerciseInformation;
    }

    public List<Exercise> getExercises(final WorkoutPlan plan) {
        final List<Exercise> exercises = exerciseRepository.findByPlan(plan);
        for (final Exercise exercise : exercises) {
            final ExerciseInformation info = exerciseInformationRepository.getExerciseInformation(exercise.getExerciseType());
            exercise.setExerciseInformation(info);
        }
        return exercises;
    }
}
