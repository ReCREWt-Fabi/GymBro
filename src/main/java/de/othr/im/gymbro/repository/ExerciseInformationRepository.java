package de.othr.im.gymbro.repository;

import de.othr.im.gymbro.model.ExerciseCategory;
import de.othr.im.gymbro.model.ExerciseInformation;

import java.util.List;

public interface ExerciseInformationRepository {

    List<ExerciseInformation> findAll();

    List<ExerciseInformation> findByCategory(final ExerciseCategory category);

    ExerciseInformation findById(final String exerciseType);

}
