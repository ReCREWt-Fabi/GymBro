package de.othr.im.gymbro.service;

import de.othr.im.gymbro.model.ExerciseCategory;
import de.othr.im.gymbro.model.ExerciseInformation;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ExerciseInformationService {
    public ExerciseInformation getExerciseInformation(String exerciseType) {
        ExerciseInformation exerciseInformation = new ExerciseInformation();
        exerciseInformation.setName("Dummy Exercise");
        exerciseInformation.setDescription("Dummy Description");
        exerciseInformation.setCategories(Arrays.stream(new ExerciseCategory[]{ExerciseCategory.CHEST, ExerciseCategory.BACK}).toList());
        return exerciseInformation;
    }
}
