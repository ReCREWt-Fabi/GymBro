package de.othr.im.gymbro.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseInformation {

    private String name;
    private String description;
    private String videoUrl;
    private List<ExerciseCategory> categories = new ArrayList<>();

    public ExerciseInformation() {
    }

    public ExerciseInformation(String name, String description, String videoUrl, List<ExerciseCategory> categories) {
        this.name = name;
        this.description = description;
        this.videoUrl = videoUrl;
        this.categories = categories;
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

    public List<ExerciseCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ExerciseCategory> categories) {
        this.categories = categories;
    }

}
