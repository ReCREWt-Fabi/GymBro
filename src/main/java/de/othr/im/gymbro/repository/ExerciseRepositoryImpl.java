package de.othr.im.gymbro.repository;

import de.othr.im.gymbro.model.ExerciseCategory;
import de.othr.im.gymbro.model.ExerciseInformation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExerciseRepositoryImpl implements ExerciseRepository {

    @Override
    public List<ExerciseInformation> getAll() {
        return null;
    }

    @Override
    public List<ExerciseInformation> getAllForCategory(ExerciseCategory category) {
        return null;
    }

    @Override
    public ExerciseInformation getExerciseInformation(String exerciseType) {
        return null;
    }
}
