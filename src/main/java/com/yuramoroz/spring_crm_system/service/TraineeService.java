package com.yuramoroz.spring_crm_system.service;

import com.yuramoroz.spring_crm_system.repository.TraineeDao;
import com.yuramoroz.spring_crm_system.entity.Trainee;
import com.yuramoroz.spring_crm_system.utils.ProfileHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class TraineeService {

    private static long traineeIDs = 0L;

    private final TraineeDao traineeDAO;

    public TraineeService(TraineeDao traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    public Trainee createTrainee(Trainee trainee) {
        log.info("Creating Trainee");
        traineeDAO.create(trainee);
        log.info("Trainee was created successfully");
        return trainee;
    }

    public Trainee createTrainee(String firstName, String lastName, Boolean isActive, String address, LocalDate dateOfBirth) {
        Trainee trainee = new Trainee();
        trainee.setFirstName(firstName);
        trainee.setLastName(lastName);
        trainee.setIsActive(isActive);
        trainee.setAddress(address);
        trainee.setDateOfBirth(dateOfBirth);
        trainee.setPassword(ProfileHandler.generatePassword());
        trainee.setUserName(ProfileHandler.generateUsername(trainee));
        trainee.setId(++traineeIDs);
        log.info("Trainee was assembled successfully");
        return createTrainee(trainee);
    }

    public Trainee updateTrainee(long id) {
        log.info("Updating Trainee...");
        Trainee updatedTrainee =  traineeDAO.update(id);
        log.info("Updating succeed");
        return updatedTrainee;
    }

    public void deleteTrainee(Trainee trainee) {
        log.info("Deleting Trainee...");
        traineeDAO.delete(trainee);
        log.info("Deletion succeed");
    }

    public Trainee getTraineeById(long id) {
        log.info("Select Trainee by ID");
        Trainee foundTrainee =  traineeDAO.getById(id);
        log.info("Selection succeed");
        return foundTrainee;
    }

    public List<Trainee> getAllTrainees() {
        log.info("Selecting Trainee list");
        List<Trainee> resultList =  traineeDAO.getAll();
        log.info("Selection succeed");
        return resultList;
    }
}
