package com.yuramoroz.spring_crm_system.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuramoroz.spring_crm_system.entity.Trainee;
import com.yuramoroz.spring_crm_system.entity.Trainer;
import com.yuramoroz.spring_crm_system.entity.Training;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class StorageInitializer implements BeanPostProcessor {

    private final ObjectMapper mapper;

    private final Map<Long, Trainee> traineeMap;
    private final Map<Long, Trainer> trainerMap;
    private final Map<Long, Training> trainingMap;

    private final String traineeStoragePath;
    private final String trainerStoragePath;
    private final String trainingStoragePath;

    public StorageInitializer(@Qualifier("traineeStorage") Map<Long, Trainee> traineeMap,
                              @Qualifier("trainerStorage") Map<Long, Trainer> trainerMap,
                              @Qualifier("trainingStorage") Map<Long, Training> trainingMap,
                              @Value("${storage.trainee.file}") String traineeStoragePath,
                              @Value("${storage.trainer.file}") String trainerStoragePath,
                              @Value("${storage.training.file}") String trainingStoragePath,
                              ObjectMapper mapper) {
        this.traineeMap = traineeMap;
        this.trainerMap = trainerMap;
        this.trainingMap = trainingMap;
        this.traineeStoragePath = traineeStoragePath;
        this.trainerStoragePath = trainerStoragePath;
        this.trainingStoragePath = trainingStoragePath;
        this.mapper = mapper;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        switch (beanName) {
            case "traineeStorage":
                log.info("Trainee storage: starts its initializing...");
                initializeTraineeStorage();
                log.info("Trainee storage: initializing succeed");
                break;
            case "trainerStorage":
                log.info("Trainer storage: starts its initializing...");
                initializeTrainerStorage();
                log.info("Trainer storage: initializing succeed");
                break;
            case "trainingStorage":
                log.info("Training storage: starts its initializing...");
                initializeTrainingStorage();
                log.info("Training storage: initializing succeed");
                break;
            default:
                throw new IllegalArgumentException("No valid storage name was provided");
        }
        return bean;
    }

    public void initializeTraineeStorage() {

        if(traineeStoragePath == null || traineeStoragePath.isBlank()){
            log.error("Invalid value was provided for Trainee file path");
            throw new IllegalArgumentException("No proper data for Trainee file path was provided");
        }

        List<Trainee> trainees = null;
        try {
            trainees = mapper.readValue(new File(traineeStoragePath), new TypeReference<List<Trainee>>() {});
        } catch (IOException e) {
            log.error("Trainee storage: An exception arised when trying to map JSON objects");
            throw new RuntimeException(e);
        }
        trainees.forEach(trainee -> traineeMap.put(trainee.getId(), trainee));
    }

    public void initializeTrainerStorage() {

        if(trainerStoragePath == null || trainerStoragePath.isBlank()){
            log.error("Invalid value was provided for Trainer file path");
            throw new IllegalArgumentException("No proper data for Trainer file path was provided");
        }

        List<Trainer> trainers = null;
        try {
            trainers = mapper.readValue(new File(trainerStoragePath), new TypeReference<List<Trainer>>() {});
        } catch (IOException e) {
            log.error("Trainer storage: An exception arised when trying to map JSON objects");
            throw new RuntimeException(e);
        }
        trainers.forEach(trainer -> trainerMap.put(trainer.getId(), trainer));
    }

    public void initializeTrainingStorage() {

        if(trainingStoragePath == null || trainingStoragePath.isBlank()){
            log.error("Invalid value was provided for Training file path");
            throw new IllegalArgumentException("No proper data for Training file path was provided");
        }

        List<Training> trainings = null;
        try {
            trainings = mapper.readValue(new File(trainingStoragePath), new TypeReference<List<Training>>() {});
        } catch (IOException e) {
            log.error("Training storage: An exception arised when trying to map JSON objects");
            throw new RuntimeException(e);
        }
        trainings.forEach(training -> trainingMap.put(training.getId(), training));
    }
}