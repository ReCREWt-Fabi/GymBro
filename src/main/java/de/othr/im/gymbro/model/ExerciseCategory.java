package de.othr.im.gymbro.model;

public enum ExerciseCategory {
    /*
    * 0:"abductors"
    1:"abs"
    2:"adductors"
    3:"biceps"
    4:"calves"
    5:"cardiovascular system"
    6:"delts"
    7:"forearms"
    8:"glutes"
    9:"hamstrings"
    10:"lats"
    11:"levator scapulae"
    12:"pectorals"
    13:"quads"
    14:"serratus anterior"
    15:"spine"
    16:"traps"
    17:"triceps"
    18:"upper back"*/
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

    private ExerciseCategory(String label) {
        this.label = label;
    }

    public static ExerciseCategory getCategoryFromApi(String category) {
        return switch (category) {
            case "abductors" -> ABDUCTORS;
            case "abs" -> ABS;
            case "adductors" -> ADDUCTORS;
            case "biceps" -> BICEPS;
            case "calves" -> CALVES;
            case "cardiovascular system" -> CARDIOVASCULAR_SYSTEM;
            case "delts" -> DELTS;
            case "forearms" -> FOREARMS;
            case "glutes" -> GLUTES;
            case "hamstrings" -> HAMSTRINGS;
            case "lats" -> LATS;
            case "levator scapulae" -> LEVATOR_SCAPULAE;
            case "pectorals" -> PECTORALS;
            case "quads" -> QUADS;
            case "serratus anterior" -> SERRATUS_ANTERIOR;
            case "spine" -> SPINE;
            case "traps" -> TRAPS;
            case "triceps" -> TRICEPS;
            case "upper back" -> UPPER_BACK;
            default -> OTHER;
        };
    }
}
