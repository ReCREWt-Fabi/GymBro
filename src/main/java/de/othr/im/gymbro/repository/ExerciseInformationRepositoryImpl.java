package de.othr.im.gymbro.repository;

import de.othr.im.gymbro.model.ExerciseCategory;
import de.othr.im.gymbro.model.ExerciseInformation;
import de.othr.im.gymbro.model.ExerciseInformationFromApi;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class ExerciseInformationRepositoryImpl implements ExerciseInformationRepository {

    private List<ExerciseInformation> cache = new ArrayList<>();

    public ExerciseInformationRepositoryImpl() {
        cache = findAll();
    }

    @Override
    public List<ExerciseInformation> findAll() {
        if (!cache.isEmpty()) {
            return new ArrayList<>(cache);
        }
        final String uri = "";
        return getExercises(uri);
    }

    @Override
    public List<ExerciseInformation> findByCategory(final ExerciseCategory category) {
        if (!cache.isEmpty()) {
            return cache.stream().filter(ei -> ei.getCategory().equals(category)).toList();
        } else {
            final String uri = "/target/%s".formatted(category.label);
            return getExercises(uri);
        }
    }

    @Override
    public ExerciseInformation findById(final String exerciseType) {
        if (!cache.isEmpty()) {
            return cache.stream().filter(ei -> ei.getId().equals(exerciseType)).findFirst().orElse(null);
        } else {
            final String uri = "/exercise/%s".formatted(exerciseType);
            return getExercise(uri);
        }
    }

    private ExerciseInformation getExercise(String uri) {
        final WebClient client = prepareClient();
        final ExerciseInformationFromApi exercise = client.get().uri(uri).retrieve().bodyToMono(ExerciseInformationFromApi.class).block();
        if (exercise == null) {
            return null;
        }
        return ExerciseInformation.createFromApi(exercise);
    }

    private List<ExerciseInformation> getExercises(String uri) {
        final WebClient client = prepareClient();
        final ExerciseInformationFromApi[] exercises = client.get().uri(uri).retrieve().bodyToMono(ExerciseInformationFromApi[].class).block();
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
                    headers.set("X-RapidAPI-Key", "5b811e3733msh7176dabad43c68fp1605abjsn1b1464855f36");
                    headers.set("X-RapidAPI-Host", "exercisedb.p.rapidapi.com");
                }).build();
    }

}
