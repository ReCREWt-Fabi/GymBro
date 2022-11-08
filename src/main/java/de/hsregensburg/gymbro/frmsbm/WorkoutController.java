package de.hsregensburg.gymbro.frmsbm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WorkoutController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("workoutplan_generator")
    public String workoutplan_generator() {
        return "workoutplan_generator";
    }

    @RequestMapping("workout_generator")
    public String workout_generator() {
        return "workout_generator";
    }

}
