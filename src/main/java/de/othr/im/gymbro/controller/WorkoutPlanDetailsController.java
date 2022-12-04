package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.service.WorkoutPlanDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = {"/view_plan"})
public class WorkoutPlanDetailsController {
    private final WorkoutPlanDetailsService workoutPlanDetailsService;

    @Autowired
    public WorkoutPlanDetailsController(WorkoutPlanDetailsService workoutPlanDetailsService) {
        this.workoutPlanDetailsService = workoutPlanDetailsService;
    }
}
