package de.othr.im.gymbro.service;

import de.othr.im.gymbro.model.Schedule;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule getSchedule(final User user) {
        return scheduleRepository.findByUser(user).orElseGet(() -> createSchedule(user));
    }

    public Optional<Schedule> getScheduleFromId(final long id) {
        return scheduleRepository.findById(id);
    }

    private Schedule createSchedule(User user) {
        final Schedule schedule = new Schedule();
        schedule.setUser(user);
        return scheduleRepository.save(schedule);
    }

    public void updateSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }
}
