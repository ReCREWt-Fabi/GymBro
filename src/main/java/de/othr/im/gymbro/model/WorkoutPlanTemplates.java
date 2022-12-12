package de.othr.im.gymbro.model;

import java.util.List;

public enum WorkoutPlanTemplates {

    PUSH_DAY("Push Day", List.of()),
    PULL_DAY("Pull Day", List.of()),
    LEG_DAY("Leg Day", List.of()),
    CORE_WORKOUT("Core", List.of()),
    UPPER_BODY("Upper Body", List.of()),
    ;

    private final String name;
    private final List<String> exercises;

    WorkoutPlanTemplates(final String name, final List<String> exercises) {
        this.name = name;
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public List<String> getExercises() {
        return exercises;
    }
}
