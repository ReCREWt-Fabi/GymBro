package de.othr.im.gymbro.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@Table(name = "exercise_set")
public class ExerciseSet {
    @ManyToOne
    @JoinColumn(name = "idexercise")
    private Exercise exercise;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(value = 1, message = "Repetitions must be at least 1")
    private int reps;
    @Min(value = 1, message = "Weight must be at least 1")
    private int weight;
    @Temporal(TemporalType.TIMESTAMP)
    private Date completedAt;

    private String notes;

    public static ExerciseSet createForExercise(Exercise exercise) {
        ExerciseSet set = new ExerciseSet();
        set.setExercise(exercise);
        return set;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isCompleted() {
        return completedAt != null;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}