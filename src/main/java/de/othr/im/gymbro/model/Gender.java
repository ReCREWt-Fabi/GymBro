package de.othr.im.gymbro.model;

public enum Gender {
    UNSPECIFIED("Not Specified"),
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Diverse");

    public final String label;

    private Gender(String label) {
        this.label = label;
    }
}
