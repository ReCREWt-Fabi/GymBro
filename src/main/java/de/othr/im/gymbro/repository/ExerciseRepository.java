package de.othr.im.gymbro.repository;

import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Transactional
    @Modifying
    @Query("update Exercise e set e.plan = null where e.plan = ?1")
    void removeExercisesFromPlan(WorkoutPlan plan);

    List<Exercise> findByPlan(final WorkoutPlan plan);

}
