package de.othr.im.gymbro.model;

public enum WorkoutGoal {
    UNSPECIFIED("Not Specified"),
    WEIGHT_LOSS("Weight Loss"),
    WEIGHT_GAIN("Weight Gain"),
    MUSCLE_GAIN("Muscle Gain"),
    MUSCLE_MAINTENANCE("Muscle Maintenance"),
    OTHER("Other");

    public final String label;

    private WorkoutGoal(String label) {
        this.label = label;
    }
}
