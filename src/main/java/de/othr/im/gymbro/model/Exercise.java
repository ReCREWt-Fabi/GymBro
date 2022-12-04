package de.othr.im.gymbro.model;

import de.othr.im.gymbro.frmsbm.Workout;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Used to retrieve the exercise information from the ExerciseRepository.
     */
    private String exerciseType;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.MERGE)
    private List<ExerciseSet> sets = new ArrayList<>();

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.MERGE)
    private List<CompletedSet> completedSets = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "idplan")
    private WorkoutPlan plan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public List<ExerciseSet> getSets() {
        return sets;
    }

    public void setSets(List<ExerciseSet> sets) {
        this.sets = sets;
    }

    public WorkoutPlan getPlan() {
        return plan;
    }

    public void setPlan(WorkoutPlan plan) {
        this.plan = plan;
    }

    public List<CompletedSet> getCompletedSets() {
        return completedSets;
    }

    public void setCompletedSets(List<CompletedSet> completedSets) {
        this.completedSets = completedSets;
    }
}
