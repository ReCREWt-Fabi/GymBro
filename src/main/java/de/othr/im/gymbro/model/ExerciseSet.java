package de.othr.im.gymbro.model;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "exercise_set")
public class ExerciseSet {
    @ManyToOne
    @JoinColumn(name = "idexercise")
    private Exercise exercise;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    private int ordering;

    @Positive
    private int reps;

    @Positive
    private int weight;

    public ExerciseSet(){}

    public ExerciseSet(final int ordering, final int reps, final int weight, final Exercise exercise) {
        this.ordering = ordering;
        this.reps = reps;
        this.weight = weight;
        this.exercise = exercise;
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
}