package de.othr.im.gymbro.controller.rest;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.repository.UserRepository;
import de.othr.im.gymbro.rest.EntityNotFoundException;
import de.othr.im.gymbro.rest.InvalidAccessException;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping(value = {"/api/workout_plans"})
public class WorkoutPlanAPIController {
    private final WorkoutPlanService workoutPlanService;

    private final UserRepository userRepository;


    @Autowired
    public WorkoutPlanAPIController(final WorkoutPlanService workoutPlanService, UserRepository userRepository) {
        this.workoutPlanService = workoutPlanService;
        this.userRepository = userRepository;
    }

    @GetMapping("/by_user/{id}")
    public ResponseEntity<List<WorkoutPlan>> getWorkoutPlansFromUserId(@PathVariable("id") long id, @AuthenticationPrincipal GymBroUserDetails userDetails) {
        if (userDetails.getUser().getId() != id) {
            throw new InvalidAccessException();
        }
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return ResponseEntity.ok().body(workoutPlanService.getPlans(user.get()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutPlan> getWorkoutPlanById(@PathVariable("id") long id, @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final Optional<WorkoutPlan> plan = workoutPlanService.getPlan(id);
        if (plan.isEmpty()) {
            throw new EntityNotFoundException();
        }
        if (!Objects.equals(plan.get().getUser().getId(), userDetails.getUser().getId())) {
            throw new InvalidAccessException();
        }
        return ResponseEntity.ok().body(plan.get());
    }

    @PostMapping("/")
    public ResponseEntity<WorkoutPlan> createWorkoutPlan(@RequestBody final WorkoutPlan workoutPlan, @AuthenticationPrincipal final GymBroUserDetails userDetails) {
        if (!Objects.equals(workoutPlan.getUser().getId(), userDetails.getUser().getId())) {
            throw new InvalidAccessException();
        }
        final WorkoutPlan plan = workoutPlanService.insertPlan(workoutPlan);
        return ResponseEntity.ok().body(plan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutPlan> updateWorkoutPlan(@PathVariable("id") long id, @RequestBody final WorkoutPlan workoutPlan, @AuthenticationPrincipal final GymBroUserDetails userDetails) {
        final Optional<WorkoutPlan> plan = workoutPlanService.getPlan(id);
        if (plan.isEmpty()) {
            throw new EntityNotFoundException();
        }
        if (!Objects.equals(workoutPlan.getUser().getId(), userDetails.getUser().getId())) {
            throw new InvalidAccessException();
        }
        workoutPlanService.updatePlan(workoutPlan);
        return ResponseEntity.ok().body(workoutPlan);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkoutPlan(@PathVariable("id") long id, @AuthenticationPrincipal final GymBroUserDetails userDetails) {
        final Optional<WorkoutPlan> plan = workoutPlanService.getPlan(id);
        if (plan.isEmpty()) {
            throw new EntityNotFoundException();
        }
        if (!Objects.equals(plan.get().getUser().getId(), userDetails.getUser().getId())) {
            throw new InvalidAccessException();
        }
        workoutPlanService.deletePlan(id);
    }
}


