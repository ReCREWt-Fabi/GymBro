package de.othr.im.gymbro.repository;

import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
