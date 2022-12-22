package de.othr.im.gymbro.model;

import java.util.List;

public enum WorkoutPlanTemplates {

    PUSH_DAY("Push Day", List.of("0314", "0025", "0251", "0227", "0178", "0334", "0241", "0060", "0073")),
    PULL_DAY("Pull Day", List.of("0027", "0095", "0651", "1326", "0198", "0315", "0489")),
    LEG_DAY("Leg Day", List.of("0410", "0043", "0032", "1409", "0594", "0593", "0739")),
    CORE_WORKOUT("Core", List.of("0277", "0472", "0407")),
    UPPER_BODY("Upper Body", List.of("0314", "3287", "0383", "0150", "0294", "0977", "1274")),
    ;

    private final String name;
    private final List<String> exercise_ids;

    WorkoutPlanTemplates(final String name, final List<String> exercises) {
        this.name = name;
        this.exercise_ids = exercises;
    }

    public String getName() {
        return name;
    }

    public List<String> getExercises() {
        return exercise_ids;
    }
}
