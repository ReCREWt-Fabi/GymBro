package de.othr.im.gymbro.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "completed_set")
public class CompletedSet {
    @ManyToOne
    @JoinColumn(name = "idexercise")
    private Exercise exercise;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int reps;

    private int weight;

    @Temporal(TemporalType.TIMESTAMP)
    private Date completedAt;

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

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }
}