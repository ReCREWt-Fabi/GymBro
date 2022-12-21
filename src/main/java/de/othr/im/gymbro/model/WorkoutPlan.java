package de.othr.im.gymbro.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.*;

import static java.lang.System.in;

@Entity
@Table(name = "workout_plan")
public class WorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, message = "Name must contain at least 4 characters!")
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "iduser", referencedColumnName = "id")
    @Valid
    private User user;

    @ManyToMany
    @JoinTable(
            name = "plan_started_by",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_id"))
    private List<User> startedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastStartedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastCompletedAt;

    @ElementCollection(targetClass = Weekday.class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private Set<Weekday> days = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "plan_follower",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_id"))
    private List<User> followers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public Date getLastStartedAt() {
        return lastStartedAt;
    }

    public void setLastStartedAt(Date lastStartedAt) {
        this.lastStartedAt = lastStartedAt;
    }

    public Date getLastCompletedAt() {
        return lastCompletedAt;
    }

    public void setLastCompletedAt(Date lastCompletedAt) {
        this.lastCompletedAt = lastCompletedAt;
    }

    public boolean isRunning(User user) {
        return this.inStartedBy(user) && getLastStartedAt() != null && (getLastCompletedAt() == null ||
                getLastCompletedAt().before(getLastStartedAt()));
    }

    public Set<Weekday> getDays() {
        return days;
    }

    public void setDays(Set<Weekday> days) {
        this.days = days;
    }

    public void setStarted_by(List<User> users) { this.startedBy = users; }

    public List<User> getStarted_by() { return this.startedBy; }

    public void addStartedBy(User user) {
        if (!this.inStartedBy(user)) {
            this.startedBy.add(user);
        }
    }

    public boolean removeStartedBy(User user) {
        boolean result;
        if(this.startedBy.remove(user)) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public boolean inStartedBy(User user) {
        boolean result = false;
        for(User scope : this.startedBy) {
            if (Objects.equals(scope.getId(), user.getId())) {
                result = true;
                break;
            }
        }
        return result;
    }
}
