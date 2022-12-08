package de.othr.im.gymbro.repository;

import de.othr.im.gymbro.model.ExerciseCategory;
import de.othr.im.gymbro.model.ExerciseInformation;

import java.util.List;

public interface ExerciseInformationRepository {

    List<ExerciseInformation> getAll();

    List<ExerciseInformation> getAllForCategory(final ExerciseCategory category);

    ExerciseInformation getExerciseInformation(final String exerciseType);

}
