package de.othr.im.gymbro.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@Table(name = "exercise_set")
public class ExerciseSet {
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JoinColumn(name = "idexercise")
    private Exercise exercise;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int ordering;

    @Positive
    private int reps;

    @Positive
    private int weight;
    @Temporal(TemporalType.TIMESTAMP)
    private Date completedAt;

    private String notes;

    public ExerciseSet() {
    }

    public ExerciseSet(final int ordering, final int reps, final int weight, final Exercise exercise) {
        this.ordering = ordering;
        this.reps = reps;
        this.weight = weight;
        this.exercise = exercise;
    }

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

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer order) {
        this.ordering = order;
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