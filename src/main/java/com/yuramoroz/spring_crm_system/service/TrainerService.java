package com.yuramoroz.spring_crm_system.service;

import com.yuramoroz.spring_crm_system.repository.TrainerDao;
import com.yuramoroz.spring_crm_system.entity.Trainer;
import com.yuramoroz.spring_crm_system.utils.ProfileLoginAndPasswordGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TrainerService{

    private final TrainerDao trainerDAO;

    public Trainer createTrainer(Trainer trainer){
        log.info("Creating Trainer");
        trainer.setPassword(ProfileLoginAndPasswordGenerator.generatePassword());
        trainer.setUserName(ProfileLoginAndPasswordGenerator.generateUsername(trainer, trainerDAO::ifTrainerExistByUsername));
        return trainerDAO.create(trainer);
    }

    public Trainer createTrainer(String firstName, String lastName, Boolean isActive, String specialization){
        Trainer trainer = new Trainer();
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setActive(isActive);
        trainer.setSpecialization(specialization);
        log.info("Trainer was assembled successfully");
        return createTrainer(trainer);
    }

    public Trainer updateTrainer(Trainer trainer){
        log.info("Updating Trainer...");
        return trainerDAO.update(trainer);
    }

    public Trainer getTrainerById(long id){
        log.info("Selecting Trainer by ID");
        return trainerDAO.getById(id);
    }

    public List<Trainer> getAllTrainers(){
        log.info("Selecting Trainer list");
        return trainerDAO.getAll();
    }
}
