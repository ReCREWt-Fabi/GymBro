package de.othr.im.gymbro.model;

import java.util.List;

public enum WorkoutPlanTemplates {

    PUSH_DAY("Push Day", List.of("0001", "0002", "0003")),
    PULL_DAY("Pull Day", List.of("0004", "0005", "0006")),
    LEG_DAY("Leg Day", List.of("0007", "0008", "0009")),
    CORE_WORKOUT("Core", List.of("0010", "0011", "0012")),
    UPPER_BODY("Upper Body", List.of("0013", "0014", "0015")),
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
