package com.yuramoroz.spring_crm_system.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuramoroz.spring_crm_system.entity.Trainee;
import com.yuramoroz.spring_crm_system.entity.Trainer;
import com.yuramoroz.spring_crm_system.entity.Training;
import com.yuramoroz.spring_crm_system.service.TraineeService;
import com.yuramoroz.spring_crm_system.service.TrainerService;
import com.yuramoroz.spring_crm_system.service.TrainingService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class StorageInitializer{

    private final TraineeService traineeService;

    private final TrainerService trainerService;

    private final TrainingService trainingService;

    private final ObjectMapper mapper;

    private final String traineeStoragePath;
    private final String trainerStoragePath;
    private final String trainingStoragePath;

    public StorageInitializer(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService,
                              @Value("${storage.trainees.file}") String traineeStoragePath,
                              @Value("${storage.trainers.file}") String trainerStoragePath,
                              @Value("${storage.trainings.file}") String trainingStoragePath,
                              ObjectMapper mapper) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.traineeStoragePath = traineeStoragePath;
        this.trainerStoragePath = trainerStoragePath;
        this.trainingStoragePath = trainingStoragePath;
        this.mapper = mapper;
    }

    @PostConstruct
    public void initializeAllStorages(){
        log.info("Trainee storage: starts its initializing...");
        initializeTraineeStorage();

        log.info("Trainer storage: starts its initializing...");
        initializeTrainerStorage();

        log.info("Training storage: starts its initializing...");
        initializeTrainingStorage();
    }

    private void initializeTraineeStorage() {

        if (traineeStoragePath == null || traineeStoragePath.isBlank()) {
            log.error("Invalid value was provided for Trainee file path");
            throw new IllegalArgumentException("No proper data for Trainee file path was provided");
        }

        List<Trainee> trainees = null;
        try {
            trainees = mapper.readValue(new File(traineeStoragePath), new TypeReference<List<Trainee>>() {
            });
        } catch (IOException e) {
            log.error("Trainee storage: An exception arised when trying to map JSON objects");
            throw new RuntimeException(e);
        }
        trainees.forEach(traineeService::createTrainee);
    }

    private void initializeTrainerStorage() {

        if (trainerStoragePath == null || trainerStoragePath.isBlank()) {
            log.error("Invalid value was provided for Trainer file path");
            throw new IllegalArgumentException("No proper data for Trainer file path was provided");
        }

        List<Trainer> trainers = null;
        try {
            trainers = mapper.readValue(new File(trainerStoragePath), new TypeReference<List<Trainer>>() {
            });
        } catch (IOException e) {
            log.error("Trainer storage: An exception arised when trying to map JSON objects");
            throw new RuntimeException(e);
        }
        trainers.forEach(trainerService::createTrainer);
    }

    private void initializeTrainingStorage() {

        if (trainingStoragePath == null || trainingStoragePath.isBlank()) {
            log.error("Invalid value was provided for Training file path");
            throw new IllegalArgumentException("No proper data for Training file path was provided");
        }

        List<Training> trainings = null;
        try {
            trainings = mapper.readValue(new File(trainingStoragePath), new TypeReference<List<Training>>() {
            });
        } catch (IOException e) {
            log.error("Training storage: An exception arised when trying to map JSON objects");
            throw new RuntimeException(e);
        }
        trainings.forEach(trainingService::createTraining);
    }
}