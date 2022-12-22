package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.repository.UserRepository;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = {"/workout_plans/api"})
public class WorkoutPlanAPIController {
    private final WorkoutPlanService workoutPlanService;

    private final UserRepository userRepository;


    @Autowired
    public WorkoutPlanAPIController(final WorkoutPlanService workoutPlanService,
                                    UserRepository userRepository) {
        this.workoutPlanService = workoutPlanService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<WorkoutPlan>> getWorkoutPlansFromUserId(@RequestParam("id") long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> ResponseEntity.ok().body(workoutPlanService.getPlans(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
