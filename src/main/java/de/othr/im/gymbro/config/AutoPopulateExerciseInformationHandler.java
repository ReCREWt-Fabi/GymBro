package de.othr.im.gymbro.config;

import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.ExerciseInformation;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.repository.ExerciseInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AutoPopulateExerciseInformationHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ExerciseInformationRepository exerciseInformationRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final GymBroUserDetails userDetails = (GymBroUserDetails) authentication.getPrincipal();
        final User user = userDetails.getUser();
        for (final WorkoutPlan workoutPlan : user.getPlans()) {
            for (final Exercise exercise : workoutPlan.getExercises()) {
                final String exerciseType = exercise.getExerciseType();
                final ExerciseInformation information = exerciseInformationRepository.findById(exerciseType);
                exercise.setExerciseInformation(information);
            }
        }
    }
}
