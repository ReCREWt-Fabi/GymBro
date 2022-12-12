package de.othr.im.gymbro.model;

public enum ExerciseCategory {
    ABDUCTORS("Abductors"),
    ABS("Abs"),
    ADDUCTORS("Adductors"),
    BICEPS("Biceps"),
    CALVES("Calves"),
    CARDIOVASCULAR_SYSTEM("Cardiovascular System"),
    DELTS("Delts"),
    FOREARMS("Forearms"),
    GLUTES("Glutes"),
    HAMSTRINGS("Hamstrings"),
    LATS("Lats"),
    LEVATOR_SCAPULAE("Levator scapulae"),
    PECTORALS("Pectorals"),
    QUADS("Quads"),
    SERRATUS_ANTERIOR("Serratus Anterior"),
    SPINE("Spine"),
    TRAPS("Traps"),
    TRICEPS("Triceps"),
    UPPER_BACK("Upper Back"),
    OTHER("Other");


    public final String label;

    ExerciseCategory(String label) {
        this.label = label;
    }

    public static ExerciseCategory getCategoryFromApi(String category) {
        return ExerciseCategory.valueOf(category.toUpperCase().replace(" ", "_"));
    }

    public String getLabel() {
        return label;
    }
}
