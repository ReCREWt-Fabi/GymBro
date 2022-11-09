package de.hsregensburg.gymbro.frmsbm;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import java.util.Random;


@Controller
public class WorkoutController {

    private WorkoutPlan user_plan;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("workoutplan_generator")
    public String workoutplan_generator(
            @RequestParam(required = false) String workoutplan_name
    ) {
        if(workoutplan_name == null) {
            Random rand = new Random();
            int n = rand.nextInt(10000);
            workoutplan_name = "Workout" + Integer.toString(n);
        }
        this.user_plan = new WorkoutPlan(workoutplan_name);
        return "workoutplan_generator";
    }

    @RequestMapping( value = "workout_generator")
    public String workout_generator() {
        return "workout_generator";
    }

    @RequestMapping(value = "updated_workoutplan", method = RequestMethod.GET)
    public ModelAndView added_workout(
            @RequestParam String wname,
            @RequestParam String wkdy,
            @RequestParam int starter,
            @RequestParam int ender
    ) {
        Workout new_workout = new Workout(starter, ender, wkdy, wname);
        this.user_plan.addWorkout(new_workout);
        this.user_plan.printWorkouts();
        return new ModelAndView("workoutplan_generator");
    }

    @RequestMapping(value = "template_used", method = RequestMethod.GET)
    public ModelAndView used_template(
            @RequestParam int template
    ) {
        this.user_plan.fillPlanUsingTemplate(template);
        return new ModelAndView("workoutplan_generator");
    }

    @RequestMapping(value = "print_plan", method = RequestMethod.GET)
    public ModelAndView print_plan() {
        this.user_plan.printWorkouts();
        return new ModelAndView("workoutplan_generator");
    }

}
