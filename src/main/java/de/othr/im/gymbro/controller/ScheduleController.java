package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.Schedule;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.Weekday;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.service.ScheduleService;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = {"/schedule"})
public class ScheduleController {
    private final WorkoutPlanService workoutPlanService;
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(WorkoutPlanService workoutPlanService, ScheduleService scheduleService) {
        this.workoutPlanService = workoutPlanService;
        this.scheduleService = scheduleService;
    }

    @RequestMapping({"", "/"})
    public String showScheduleScreen(final Model model, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final List<WorkoutPlan> plans = workoutPlanService.getPlans(userDetails.getUser());
        final Schedule schedule = scheduleService.getSchedule(userDetails.getUser());
        boolean break_impossible = false;
        if (schedule.getPauseFrom() != null && schedule.getPauseTo() != null) {
            break_impossible = schedule.getPauseFrom().after(schedule.getPauseTo()) ||
                    schedule.getPauseTo().before(new Date());
        }
        model.addAttribute("plans", plans);
        model.addAttribute("service", workoutPlanService);
        model.addAttribute("days", Weekday.values());
        model.addAttribute("break_error", break_impossible);
        if(break_impossible) {
            schedule.setPauseFrom(null);
            schedule.setPauseTo(null);
            scheduleService.updateSchedule(schedule);
        }
        model.addAttribute("schedule", schedule);
        return "workout_plans/schedule";
    }

    @RequestMapping({"/day_selected"})
    public String daySelected(final Model model, @RequestParam final Long planId, @RequestParam final Weekday day, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final WorkoutPlan plan = workoutPlanService.getPlan(planId).orElseThrow();
        plan.getDays().add(day);
        workoutPlanService.updatePlan(plan);
        return showScheduleScreen(model, userDetails);
    }

    @RequestMapping({"/day_deselected"})
    public String dayDeselected(final Model model, @RequestParam final Long planId, @RequestParam final Weekday day, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final WorkoutPlan plan = workoutPlanService.getPlan(planId).orElseThrow();
        plan.getDays().remove(day);
        workoutPlanService.updatePlan(plan);
        return showScheduleScreen(model, userDetails);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/break")
    public ModelAndView breakAdded(@Valid @ModelAttribute("schedule") final Schedule schedule, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/schedule");
        }
        scheduleService.updateSchedule(schedule);
        return new ModelAndView("redirect:/schedule");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/break_cancel")
    public ModelAndView breakAdded(final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final Schedule schedule = scheduleService.getSchedule(userDetails.getUser());
        schedule.setPauseFrom(null);
        schedule.setPauseTo(null);
        scheduleService.updateSchedule(schedule);
        return new ModelAndView("redirect:/schedule");
    }
}
