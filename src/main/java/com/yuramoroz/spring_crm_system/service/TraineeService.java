package com.yuramoroz.spring_crm_system.service;

import com.yuramoroz.spring_crm_system.repository.TraineeDao;
import com.yuramoroz.spring_crm_system.entity.Trainee;
import com.yuramoroz.spring_crm_system.utils.ProfileLoginAndPasswordGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TraineeService {

    private final TraineeDao traineeDAO;
    private final ProfileLoginAndPasswordGenerator loginAndPasswordGenerator;

    public Trainee createTrainee(Trainee trainee) {
        log.info("Creating Trainee");
        trainee.setPassword(loginAndPasswordGenerator.generatePassword());
        trainee.setUserName(loginAndPasswordGenerator.generateUsername(trainee));
        return traineeDAO.create(trainee);
    }

    public Trainee createTrainee(String firstName, String lastName, Boolean isActive, String address, LocalDate dateOfBirth) {
        Trainee trainee = new Trainee();
        trainee.setFirstName(firstName);
        trainee.setLastName(lastName);
        trainee.setActive(isActive);
        trainee.setAddress(address);
        trainee.setDateOfBirth(dateOfBirth);
        log.info("Trainee was assembled successfully");
        return createTrainee(trainee);
    }

    public Trainee updateTrainee(Trainee trainee) {
        log.info("Updating Trainee...");
        return traineeDAO.update(trainee);
    }

    public void deleteTrainee(Trainee trainee) {
        log.info("Deleting Trainee...");
        traineeDAO.delete(trainee);
    }

    public Trainee getTraineeById(long id) {
        log.info("Select Trainee by ID");
        return traineeDAO.getById(id);
    }

    public List<Trainee> getAllTrainees() {
        log.info("Selecting Trainee list");
        return traineeDAO.getAll();
    }
}
