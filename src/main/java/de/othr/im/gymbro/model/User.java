package de.othr.im.gymbro.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;
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

    @ManyToMany(mappedBy = "followers", fetch = FetchType.EAGER)
    private List<WorkoutPlan> followedPlans;

    // @NotZeroEnum(message = "Please specify your gender")
    private Gender gender = Gender.UNSPECIFIED;

    // @NotZeroEnum(message = "Please tell us you main workout goal")
    private WorkoutGoal workoutGoal = WorkoutGoal.UNSPECIFIED;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // @Past(message = "Birth date must be in the past!")
    // @NotNull(message = "Please specify a birth date!")
    private LocalDate birthDate = LocalDate.now();

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

    public List<WorkoutPlan> getFollowedPlans() {
        return followedPlans;
    }

    public void setFollowedPlans(List<WorkoutPlan> followedPlans) {
        this.followedPlans = followedPlans;
    }
}
