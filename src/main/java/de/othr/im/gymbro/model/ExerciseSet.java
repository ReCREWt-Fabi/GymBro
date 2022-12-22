package de.othr.im.gymbro.model;

import org.thymeleaf.expression.Lists;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "exercise_set")
public class ExerciseSet {
    @ManyToOne
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

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "iduser", referencedColumnName = "id")
    @Valid
    private User user;


    public ExerciseSet() {
    }

    public ExerciseSet(final int ordering, final int reps, final int weight, final Exercise exercise) {
        this.ordering = ordering;
        this.reps = reps;
        this.weight = weight;
        this.exercise = exercise;
    }

    public static ExerciseSet createForExercise(Exercise exercise, List<ExerciseSet> completedSets, int nextIndex) {
        final ExerciseSet lastSet = completedSets.stream().max(Comparator.comparing(ExerciseSet::getCompletedAt)).orElse(new ExerciseSet());
        ExerciseSet set = new ExerciseSet();
        set.setOrdering(nextIndex);
        set.setExercise(exercise);
        set.setReps(lastSet.getReps());
        set.setWeight(lastSet.getWeight());
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

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}