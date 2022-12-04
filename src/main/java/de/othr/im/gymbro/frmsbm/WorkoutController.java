package de.othr.im.gymbro.frmsbm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
            @RequestParam(required = false) String workoutplan_name,
            Model model
    ) {
        if(workoutplan_name == null) {
            Random rand = new Random();
            int n = rand.nextInt(10000);
            workoutplan_name = "Workout" + Integer.toString(n);
        }
        // when user registration/login is implemented, this has to be changed.
        this.user_plan = new WorkoutPlan(workoutplan_name);
        model.addAttribute("workouts", this.user_plan.getWorkouts());
        return "workoutplan_generator";
    }

    @RequestMapping( value = "workout_generator")
    public String workout_generator() {
        return "workout_generator";
    }

    @RequestMapping(value = "edited_workoutplan", method = RequestMethod.GET)
    public String edited_workout(
            @RequestParam String wname,
            @RequestParam String wkdy,
            @RequestParam int starter,
            @RequestParam int ender,
            @RequestParam String old_title,
            Model model
    ) {

        this.user_plan.updateWorkoutPlan(old_title, starter, ender, wkdy, wname);
        model.addAttribute("workouts", this.user_plan.getWorkouts());
        return "workoutplan_generator";
    }
    @RequestMapping(value = "updated_workoutplan", method = RequestMethod.GET)
    public String added_workout(
            @RequestParam String wname,
            @RequestParam String wkdy,
            @RequestParam int starter,
            @RequestParam int ender,
            Model model
    ) {

        Workout new_workout = new Workout(starter, ender, wkdy, wname);
        this.user_plan.addWorkout(new_workout);
        this.user_plan.printWorkouts();
        model.addAttribute("workouts", this.user_plan.getWorkouts());
        return "workoutplan_generator";
    }

    @RequestMapping("/deleted_workoutplan")
    public String deleted_workout(
        @RequestParam String del_title,
        Model model
    ) {
        this.user_plan.deleteWorkout(del_title);
        model.addAttribute("workouts", this.user_plan.getWorkouts());
        return "workoutplan_generator";
    }

    @RequestMapping(value = "template_used", method = RequestMethod.GET)
    public String used_template(
            @RequestParam int template,
            Model model
    ) {
        this.user_plan.fillPlanUsingTemplate(template);
        model.addAttribute("workouts", this.user_plan.getWorkouts());
        return "workoutplan_generator";
    }

    @RequestMapping(value = "print_plan", method = RequestMethod.GET)
    public ModelAndView print_plan() {
        this.user_plan.printWorkouts();
        return new ModelAndView("workoutplan_generator");
    }

    @RequestMapping(value = "workout_generator/edit")
    public String edit_plan(
            @RequestParam String title,
            Model model
    ) {
        model.addAttribute("workout", this.user_plan.getWorkoutFromName(title));
        return "workout_editor";
    }
}
