package de.othr.im.gymbro.repository;

import de.othr.im.gymbro.model.ExerciseCategory;
import de.othr.im.gymbro.model.ExerciseInformation;
import de.othr.im.gymbro.model.ExerciseInformationFromApi;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ExerciseInformationRepositoryImpl implements ExerciseInformationRepository {

    @Override
    public List<ExerciseInformation> findAll() {
        String uri = "";
        return getExercises(uri);
    }

    @Override
    public List<ExerciseInformation> findByCategory(final ExerciseCategory category) {
        String uri = "/target/%s".formatted(category.label);
        return getExercises(uri);
    }

    @Override
    public ExerciseInformation findById(final String exerciseType) {
        String uri = "/exercise/%s".formatted(exerciseType);
        return getExercise(uri);
    }

    private ExerciseInformation getExercise(String uri) {
        WebClient client = prepareClient();
        ExerciseInformationFromApi exercise = client.get().uri(uri).retrieve().bodyToMono(ExerciseInformationFromApi.class).block();
        if (exercise == null) {
            return null;
        }

        return ExerciseInformation.createFromApi(exercise);
    }

    private List<ExerciseInformation> getExercises(String uri) {
        WebClient client = prepareClient();
        ExerciseInformationFromApi[] exercises = client.get().uri(uri).retrieve().bodyToMono(ExerciseInformationFromApi[].class).block();
        if (exercises == null) {
            return Collections.emptyList();
        }
        
        return Arrays.stream(exercises).map(ExerciseInformation::createFromApi).toList();
    }

    private WebClient prepareClient() {
        String uri = "https://exercisedb.p.rapidapi.com/exercises";

        return WebClient.builder()
                .baseUrl(uri).defaultHeaders(headers -> {
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.set("X-RapidAPI-Key", "514266226dmsh07fca84de2fcd5cp1f3ff2jsn62b9edc1ee71");
                    headers.set("X-RapidAPI-Host", "exercisedb.p.rapidapi.com");
                }).build();
    }

}
