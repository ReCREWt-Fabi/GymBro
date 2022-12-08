package de.othr.im.gymbro.model;

public enum ExerciseCategory {
    CHEST("Chest"),
    BACK("Back"),
    LEGS("Legs"),
    SHOULDERS("Shoulders"),
    BICEPS("Biceps"),
    TRICEPS("Triceps"),
    ABS("Abs"),
    CARDIO("Cardio"),
    OTHER("Other");

    public final String label;

    private ExerciseCategory(String label) {
        this.label = label;
    }
}
