package de.othr.im.gymbro.repository;

import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findByPlan(final WorkoutPlan plan);

}
