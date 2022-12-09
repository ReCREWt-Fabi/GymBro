package de.othr.im.gymbro.config;

import de.othr.im.gymbro.model.Exercise;
import de.othr.im.gymbro.model.ExerciseInformation;
import de.othr.im.gymbro.model.User;
import de.othr.im.gymbro.model.WorkoutPlan;
import de.othr.im.gymbro.repository.ExerciseInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AutoPopulateExerciseInformationHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final ExerciseInformationRepository exerciseInformationRepository;

    @Autowired
    public AutoPopulateExerciseInformationHandler(ExerciseInformationRepository exerciseInformationRepository) {
        this.exerciseInformationRepository = exerciseInformationRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        final GymBroUserDetails userDetails = (GymBroUserDetails) authentication.getPrincipal();
        final User user = userDetails.getUser();
        for (final WorkoutPlan workoutPlan : user.getPlans()) {
            for (final Exercise exercise : workoutPlan.getExercises()) {
                final String exerciseType = exercise.getExerciseType();
                final ExerciseInformation information = exerciseInformationRepository.findById(exerciseType);
                exercise.setExerciseInformation(information);
            }
        }

        super.onAuthenticationSuccess(request, response, authentication);
        clearAuthenticationAttributes(request);
    }
}