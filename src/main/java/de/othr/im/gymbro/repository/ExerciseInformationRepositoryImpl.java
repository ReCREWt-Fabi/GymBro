package de.othr.im.gymbro.repository;

import de.othr.im.gymbro.model.ExerciseCategory;
import de.othr.im.gymbro.model.ExerciseInformation;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class ExerciseInformationRepositoryImpl implements ExerciseInformationRepository {

    public static final Map<String, ExerciseInformation> DUMMY_VALUES = Map.ofEntries(
            Map.entry("Bicep Curl", new ExerciseInformation("Bicep Curl", "Bicep Curl Description", "https://www.youtube.com/watch?v=4JYj1qQF6qA", Arrays.asList(ExerciseCategory.BICEPS))),
            Map.entry("Bird Dog", new ExerciseInformation("Bird Dog", "Lie on your back with your knees bent and your feet flat on the floor. Extend your arms in front of you, with your palms flat on the floor. Lift your left leg and right arm off the floor, keeping your back flat. Hold for a moment, then return to the starting position. Repeat with your right leg and left arm.", "https://www.youtube.com/watch?v=Z1Yd7upQsXY", Arrays.asList(ExerciseCategory.ABS))),
            Map.entry("Treadmill", new ExerciseInformation("Treadmill", "Run on a treadmill", "https://www.youtube.com/watch?v=Z1Yd7upQsXY", Arrays.asList(ExerciseCategory.CARDIO))),
            Map.entry("Bench Press", new ExerciseInformation("Bench Press", "Lie on a flat bench. Grasp the barbell with a shoulder-width grip. Unrack the barbell and hold it over you with your arms straight. Lower the barbell to your chest. Press the barbell back to the starting position.", "https://www.youtube.com/watch?v=4Y7Q8ZQYj6c", Arrays.asList(ExerciseCategory.CHEST, ExerciseCategory.SHOULDERS, ExerciseCategory.TRICEPS))),
            Map.entry("Lat Pulldown", new ExerciseInformation("Lat Pulldown", "The lat pulldown is a strength training exercise that targets the latissimus dorsi muscle. It is performed by pulling a bar down towards the neck, using the lats to pull the weight. The lat pulldown is a compound exercise that also works the biceps, forearms, and traps. It is a popular exercise for bodybuilders and powerlifters.", "https://www.youtube.com/watch?v=Z9Z9Z9Z9Z9Z", Arrays.asList(ExerciseCategory.BACK)))
            );

    @Override
    public List<ExerciseInformation> getAll() {
        return DUMMY_VALUES.values().stream().toList();
    }

    @Override
    public List<ExerciseInformation> getAllForCategory(final ExerciseCategory category) {
        return DUMMY_VALUES.values().stream().filter(e -> e.getCategories().contains(category)).toList();
    }

    @Override
    public ExerciseInformation getExerciseInformation(final String exerciseType) {
        return DUMMY_VALUES.get(exerciseType);
    }
}
