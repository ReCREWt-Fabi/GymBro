package de.othr.im.gymbro.model;

public enum Weekday {

    MONDAY("Mo"),
    TUESDAY("Di"),
    WEDNESDAY("Mi"),
    THURSDAY("Do"),
    FRIDAY("Fr"),
    SATURDAY("Sa"),
    SUNDAY("So");

    public final String label;

    Weekday(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
