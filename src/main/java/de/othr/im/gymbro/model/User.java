package de.othr.im.gymbro.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User  {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min =4, message = "Name must contain at least 4 characters!")
	private String name;

	@Email(message = "Please, inform a valid E-Mail!")
	private String email;

	@Size(min = 6, message = "Password must contain at least 6 characters!")
	private String password;

	@OneToMany(mappedBy = "user")
	private List<WorkoutPlan> plans = new ArrayList<>();

	@OneToOne (mappedBy = "user", cascade = CascadeType.MERGE)
	private Schedule schedule;

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
}
