package de.othr.im.gymbro.model;

public enum ExerciseCategory {
    CHEST("Chest"),
    BACK("Back"),
    LEGS("Legs"),
    SHOULDERS("Shoulders"),
    ARMS("Arms"),
    CORE("Core"),
    CARDIO("Cardio"),
    OTHER("Other");

    public final String label;

    private ExerciseCategory(String label) {
        this.label = label;
    }
}
