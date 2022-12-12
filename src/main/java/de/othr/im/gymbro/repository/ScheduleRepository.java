package de.othr.im.gymbro.repository;

import de.othr.im.gymbro.model.Schedule;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByUser(User user);
}
