package de.othr.im.gymbro.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

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

    /**
     * Automatically populated upon deserialization.
     */
    @Transient
    private ExerciseInformation exerciseInformation;

    @ManyToOne
    @JoinColumn(name = "idplan")
    private WorkoutPlan plan;

    @ManyToOne
    @JoinColumn(name = "iduser")
    private User user;

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

    public WorkoutPlan getPlan() {
        return plan;
    }

    public void setPlan(WorkoutPlan plan) {
        this.plan = plan;
    }

    public ExerciseInformation getExerciseInformation() {
        return exerciseInformation;
    }

    public void setExerciseInformation(ExerciseInformation exerciseInformation) {
        this.exerciseType = exerciseInformation.getId();
        this.exerciseInformation = exerciseInformation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
