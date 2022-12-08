package de.othr.im.gymbro.repository;

import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.List;

@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    List<WorkoutPlan> findWorkoutPlansByUser(final @Valid User user);
}
