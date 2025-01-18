package com.yuramoroz.spring_crm_system.service;

import com.yuramoroz.spring_crm_system.entity.Trainee;
import com.yuramoroz.spring_crm_system.repository.impl.TraineeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class TraineeService extends BaseUserService<Trainee>{

    @Autowired
    private final TraineeDao traineeDao;

    public TraineeService(TraineeDao traineeDao) {
        super(traineeDao);
        this.traineeDao = traineeDao;
    }

    public Trainee saveTrainee(String firstName, String lastName, String password, String address, LocalDate dateOfBirth) {
        log.info("Trying to create and save {} {} trainee...", firstName, lastName);

        Trainee trainee = Trainee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .address(address)
                .dateOfBirth(dateOfBirth)
                .build();

        trainee = super.saveUser(trainee);

        log.info("The trainee {} {} was created successfully", firstName, lastName);
        return trainee;
    }
}
