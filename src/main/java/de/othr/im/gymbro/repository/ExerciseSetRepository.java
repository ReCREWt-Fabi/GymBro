package de.othr.im.gymbro.repository;

import de.othr.im.gymbro.model.ExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {
    @Query("select e from ExerciseSet e where e.exercise.id=:idexercise")
    List<ExerciseSet> findSetsByExercise(@Param("idexercise") Long idexercise);
}
