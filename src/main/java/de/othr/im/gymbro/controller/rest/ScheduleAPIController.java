package de.othr.im.gymbro.controller.rest;

import de.othr.im.gymbro.config.GymBroUserDetails;
import de.othr.im.gymbro.model.Schedule;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.repository.UserRepository;
import de.othr.im.gymbro.rest.EntityNotFoundException;
import de.othr.im.gymbro.rest.InvalidAccessException;
import de.othr.im.gymbro.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping(value = {"/api/schedule"})
public class ScheduleAPIController {

    private final UserRepository userRepository;
    private final ScheduleService scheduleService;


    @Autowired
    public ScheduleAPIController(UserRepository userRepository,
                                 ScheduleService scheduleService) {
        this.userRepository = userRepository;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/by_user/{id}")
    public ResponseEntity<Schedule> getScheduleFromUserId(@PathVariable("id") long id,
                                                          @AuthenticationPrincipal GymBroUserDetails userDetails) {
        if (userDetails.getUser().getId() != id) {
            throw new InvalidAccessException();
        }
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return ResponseEntity.ok().body(scheduleService.getSchedule(user.get()));
    }

    @GetMapping("/{id}")
    // does not make sense really
    public ResponseEntity<Schedule> getScheduleById(@PathVariable("id") long id,
                                                    @AuthenticationPrincipal GymBroUserDetails userDetails) {
        final Optional<Schedule> schedule = scheduleService.getScheduleFromId(id);
        if (schedule.isEmpty()) {
            throw new EntityNotFoundException();
        }
        if (!Objects.equals(schedule.get().getUser().getId(), userDetails.getUser().getId())) {
            throw new InvalidAccessException();
        }
        return ResponseEntity.ok().body(schedule.get());
    }

    @PostMapping("/")
    public ResponseEntity<Schedule> createSchedule(@RequestBody final Schedule schedule,
                                                   @AuthenticationPrincipal final GymBroUserDetails userDetails) {
        if (!Objects.equals(schedule.getUser().getId(), userDetails.getUser().getId())) {
            throw new InvalidAccessException();
        }
        scheduleService.updateSchedule(schedule);
        return ResponseEntity.ok().body(schedule);
    }

    @PutMapping("/")
    public ResponseEntity<Schedule> updateSchedule(@RequestBody final Schedule schedule,
                                                   @AuthenticationPrincipal final GymBroUserDetails userDetails) {
        if (!Objects.equals(userDetails.getUser().getId(), schedule.getUser().getId())) {
            throw new InvalidAccessException();
        }
        scheduleService.updateSchedule(schedule);
        return ResponseEntity.ok().body(schedule);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable("id") long id,
                               @AuthenticationPrincipal final GymBroUserDetails userDetails) {
        final Optional<Schedule> schedule = scheduleService.getScheduleFromId(id);
        if (schedule.isEmpty()) {
            throw new EntityNotFoundException();
        }
        if (!Objects.equals(schedule.get().getUser().getId(), userDetails.getUser().getId())) {
            throw new InvalidAccessException();
        }
        schedule.get().setPauseFrom(null);
        schedule.get().setPauseTo(null);
        scheduleService.updateSchedule(schedule.get());
    }
}
