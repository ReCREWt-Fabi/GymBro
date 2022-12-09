package de.othr.im.gymbro.model;

public class ExerciseInformation {

    private String name;
    private String description;
    private String videoUrl;
    private ExerciseCategory category;

    public ExerciseInformation() {
    }

    public ExerciseInformation(String name, String description, String videoUrl, ExerciseCategory category) {
        this.name = name;
        this.description = description;
        this.videoUrl = videoUrl;
        this.category = category;
    }

    public static ExerciseInformation createFromApi(ExerciseInformationFromApi exerciseInformationFromApi) {
        ExerciseInformation exerciseInformation = new ExerciseInformation();
        exerciseInformation.setName(exerciseInformationFromApi.getName());
        exerciseInformation.setDescription("Equipment: %s - Body Part: %s".formatted(exerciseInformationFromApi.getEquipment(), exerciseInformationFromApi.getBodyPart()));
        exerciseInformation.setVideoUrl(exerciseInformationFromApi.getGifUrl());
        exerciseInformation.setCategory(ExerciseCategory.getCategoryFromApi(exerciseInformationFromApi.getTarget()));
        return exerciseInformation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public ExerciseCategory getCategory() {
        return category;
    }

    public void setCategory(ExerciseCategory category) {
        this.category = category;
    }
}
