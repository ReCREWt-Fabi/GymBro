package de.othr.im.gymbro.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, message = "Name must contain at least 4 characters!")
    private String name;

    @Email(message = "Please, inform a valid E-Mail!")
    private String email;

    @Size(min = 6, message = "Password must contain at least 6 characters!")
    private String password;

    @Min(value = 0, message = "Height must be positive!")
    @Max(value = 299, message = "Height must be less than 300 cm!")
    private int height;

    @Min(value = 0, message = "Height must be positive!")
    @Max(value = 999, message = "Height must be less than 1000 kg!")
    private int weight;

    @Min(value = 0, message = "Height must be positive!")
    @Max(value = 5, message = "Height must be max. 5 min!")
    private int restTime;

    @OneToMany(mappedBy = "user")
    private List<WorkoutPlan> plans = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.MERGE)
    private Schedule schedule;

    private Gender gender;

    private WorkoutGoal workoutGoal;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public WorkoutGoal getWorkoutGoal() {
        return workoutGoal;
    }

    public void setWorkoutGoal(WorkoutGoal workoutGoal) {
        this.workoutGoal = workoutGoal;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WorkoutPlan> getPlans() {
        return plans;
    }

    public void setPlans(List<WorkoutPlan> plans) {
        this.plans = plans;
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weigth) {
        this.weight = weigth;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

}
