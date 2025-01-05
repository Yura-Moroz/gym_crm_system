package com.yuramoroz.spring_crm_system.service;

import com.yuramoroz.spring_crm_system.entity.TrainingType;
import com.yuramoroz.spring_crm_system.repository.TrainingDao;
import com.yuramoroz.spring_crm_system.entity.Training;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class TrainingService {

    private static long trainingIDs = 0;

    private final TrainingDao trainingDAO;

    public TrainingService(TrainingDao trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    public Training createTraining(Training training){
        trainingDAO.create(training);
        log.info("Training was created successfully");
        return training;
    }

    public Training createTraining(long traineeId, long trainerId, String trainingName,
                                   TrainingType trainingType, LocalDateTime trainingDate, Duration trainingDuration){
        Training training = new Training();
        training.setTraineeId(traineeId);
        training.setTrainerId(trainerId);
        training.setTrainingName(trainingName);
        training.setTrainingType(trainingType);
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(trainingDuration);
        training.setId(++trainingIDs);
        log.info("Training was assembled successfully");
        return createTraining(training);
    }

    public Training getTrainingById(Long id){
        log.info("Selecting Training by ID");
        Training training = trainingDAO.getById(id);
        log.info("Selection succeed");
        return training;
    }

    public List<Training> getAllTrainings(){
        log.info("Selecting Training list");
        List<Training> trainings = trainingDAO.getAll();
        log.info("Selection succeed");
        return trainings;
    }
}
