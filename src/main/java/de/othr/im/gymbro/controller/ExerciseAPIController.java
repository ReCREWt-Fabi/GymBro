package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.repository.UserRepository;
import de.othr.im.gymbro.repository.WorkoutPlanRepository;
import de.othr.im.gymbro.rest.EntityNotFoundException;
import de.othr.im.gymbro.rest.InvalidAccessException;
import de.othr.im.gymbro.rest.NotLoggedInException;
import de.othr.im.gymbro.service.ExerciseService;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = {"/api/exercises"})
public class ExerciseAPIController {

    private final ExerciseService exerciseService;
    private final WorkoutPlanService workoutPlanService;

    @Autowired
    public ExerciseAPIController(ExerciseService exerciseService, WorkoutPlanService workoutPlanService) {
        this.exerciseService = exerciseService;
        this.workoutPlanService = workoutPlanService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable("id") long id, @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final Optional<Exercise> exercise = Optional.ofNullable(exerciseService.getExercise(id));
        if (exercise.isEmpty()) {
            throw new EntityNotFoundException();
        }
        final User user = exercise.get().getPlan().getUser();
        if (!Objects.equals(user.getId(), userDetails.getUser().getId())) {
            throw new InvalidAccessException();
        }
        return ResponseEntity.ok().body(exercise.get());
    }

    @GetMapping("/by_workout_plan/{id}")
    public ResponseEntity<List<Exercise>> getExercisesByWorkoutPlanId(@PathVariable("id") long id, @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final Optional<WorkoutPlan> workoutPlan = workoutPlanService.getPlan(id);
        if (workoutPlan.isEmpty()) {
            throw new EntityNotFoundException();
        }
        final User user = workoutPlan.get().getUser();
        if (!Objects.equals(user.getId(), userDetails.getUser().getId())) {
            throw new InvalidAccessException();
        }
        final List<Exercise> exercises = exerciseService.getExercisesForPlan(workoutPlan.get());
        return ResponseEntity.ok().body(exercises);
    }

    @PostMapping("/by_workout_plan/{id}")
    public ResponseEntity<Exercise> createExercise(@PathVariable("id") long id, @RequestBody Exercise exercise, @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final Optional<WorkoutPlan> workoutPlan = workoutPlanService.getPlan(id);
        if (workoutPlan.isEmpty()) {
            throw new EntityNotFoundException();
        }
        final User user = workoutPlan.get().getUser();
        if (!Objects.equals(user.getId(), userDetails.getUser().getId())) {
            throw new InvalidAccessException();
        }
        exercise.setPlan(workoutPlan.get());
        final Exercise savedExercise = exerciseService.upsert(exercise);
        return ResponseEntity.ok().body(savedExercise);
    }

    @PutMapping("{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable("id") long id, @RequestBody Exercise exercise, @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final Optional<Exercise> exerciseOptional = Optional.ofNullable(exerciseService.getExercise(id));
        if (exerciseOptional.isEmpty()) {
            throw new EntityNotFoundException();
        }
        final User user = exerciseOptional.get().getPlan().getUser();
        if (!Objects.equals(user.getId(), userDetails.getUser().getId())) {
            throw new InvalidAccessException();
        }
        final Exercise savedExercise = exerciseService.upsert(exercise);
        return ResponseEntity.ok().body(savedExercise);
    }
}
