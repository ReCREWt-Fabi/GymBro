package de.othr.im.gymbro.controller.rest;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.ShareWithUserRequest;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.repository.UserRepository;
import de.othr.im.gymbro.rest.EntityAlreadyExistsException;
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
@RequestMapping(value = {"/api/social/workout_plans"})
public class FollowedPlansAPIController {

    private final UserRepository userRepository;

    private final WorkoutPlanService workoutPlanService;

    @Autowired
    public FollowedPlansAPIController(UserRepository userRepository, WorkoutPlanService workoutPlanService) {
        this.userRepository = userRepository;
        this.workoutPlanService = workoutPlanService;
    }

    @GetMapping("/by_user/{id}")
    public ResponseEntity<List<WorkoutPlan>> getFollowedWorkoutsFromUserId(@PathVariable("id") long id,
                                                                           @AuthenticationPrincipal GymBroUserDetails userDetails) {
        if (userDetails.getUser().getId() != id) {
            throw new InvalidAccessException();
        }

        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return ResponseEntity.ok().body(user.get().getFollowedPlans());
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<WorkoutPlan> followWorkout(@PathVariable("id") long id,
                                                     @AuthenticationPrincipal final GymBroUserDetails user) {
        Optional<WorkoutPlan> plan = workoutPlanService.getPlan(id);
        if (plan.isEmpty()) {
            throw new EntityNotFoundException();
        }

        boolean isUserFollowing = plan.get().getFollowers().stream().anyMatch(u -> Objects.equals(u.getId(), user.getUser().getId()));
        if (isUserFollowing) {
            throw new EntityAlreadyExistsException("User already follows this plan");
        }

        plan.get().getFollowers().add(user.getUser());
        workoutPlanService.updatePlan(plan.get());

        return ResponseEntity.ok().body(plan.get());
    }

    @PostMapping("/share/{id}")
    public ResponseEntity<WorkoutPlan> shareWorkout(@PathVariable("id") long id, @RequestBody final ShareWithUserRequest to) {
        if (to.getEmail() == null) {
            throw new EntityNotFoundException();
        }

        Optional<WorkoutPlan> plan = workoutPlanService.getPlan(id);
        if (plan.isEmpty()) {
            throw new EntityNotFoundException();
        }

        boolean isUserFollowing = plan.get().getFollowers()
                .stream()
                .anyMatch(u -> Objects.equals(u.getEmail().toLowerCase(), to.getEmail().toLowerCase()));

        if (isUserFollowing) {
            throw new EntityAlreadyExistsException("User already invited to this plan");
        }

        workoutPlanService.sharePlan(id, to.getEmail());

        return ResponseEntity.ok().body(plan.get());
    }

    @DeleteMapping("/unfollow/{id}")
    public ResponseEntity<WorkoutPlan> unfollowWorkout(@PathVariable("id") long id,
                                                       @AuthenticationPrincipal final GymBroUserDetails user) {
        Optional<WorkoutPlan> plan = workoutPlanService.getPlan(id);
        if (plan.isEmpty()) {
            throw new EntityNotFoundException();
        }

        boolean isUserFollowing = plan.get().getFollowers().stream().anyMatch(u -> Objects.equals(u.getId(), user.getUser().getId()));
        if (!isUserFollowing) {
            throw new EntityNotFoundException();
        }

        plan.get().getFollowers().removeIf(u -> Objects.equals(u.getId(), user.getUser().getId()));
        workoutPlanService.updatePlan(plan.get());

        return ResponseEntity.ok().body(plan.get());
    }
}
