package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.Schedule;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.service.ScheduleService;
import de.othr.im.gymbro.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping(value = {"/"})
public class HomeController {

    private final WorkoutPlanService workoutPlanService;
    private final ScheduleService scheduleService;

    @Autowired
    public HomeController(WorkoutPlanService workoutPlanService, ScheduleService scheduleService) {
        this.workoutPlanService = workoutPlanService;
        this.scheduleService = scheduleService;
    }

    @RequestMapping({"", "/"})
    public String showHomeScreen(final Model model, final @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final Schedule schedule = scheduleService.getSchedule(userDetails.getUser());
        final List<WorkoutPlan> currentPlans = workoutPlanService.getAllPlans().stream()
                .filter(plan -> plan.isRunning(userDetails.getUser())).collect(Collectors.toList());
        model.addAttribute("plans", currentPlans);
        model.addAttribute("schedule", schedule);
        model.addAttribute("service", workoutPlanService);
        return "home";
    }

    @RequestMapping({"/about"})
    public String showAbout() {
        return "base/about";
    }

}
