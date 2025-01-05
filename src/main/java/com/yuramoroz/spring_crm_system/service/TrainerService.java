package com.yuramoroz.spring_crm_system.service;

import com.yuramoroz.spring_crm_system.repository.TrainerDao;
import com.yuramoroz.spring_crm_system.entity.Trainer;
import com.yuramoroz.spring_crm_system.utils.ProfileHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TrainerService{

    private static long trainerIDs = 0;

    private final TrainerDao trainerDAO;

    public TrainerService(TrainerDao trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    public Trainer createTrainer(Trainer trainer){
        trainerDAO.create(trainer);
        log.info("Trainer was created successfully");
        return trainer;
    }

    public Trainer createTrainer(String firstName, String lastName, Boolean isActive, String specialization){
        Trainer trainer = new Trainer();
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setIsActive(isActive);
        trainer.setSpecialization(specialization);
        trainer.setPassword(ProfileHandler.generatePassword());
        trainer.setUserName(ProfileHandler.generateUsername(trainer));
        trainer.setId(++trainerIDs);
        log.info("Trainer was assembled successfully");
        return createTrainer(trainer);
    }

    public Trainer updateTrainer(long id){
        log.info("Updating Trainer...");
        Trainer updatedTrainer = trainerDAO.update(id);
        log.info("Updating succeed");
        return updatedTrainer;
    }

    public Trainer getTrainerById(long id){
        log.info("Selecting Trainer by ID");
        Trainer trainer = trainerDAO.getById(id);
        log.info("Selection succeed");
        return trainer;
    }

    public List<Trainer> getAllTrainers(){
        log.info("Selecting Trainer list");
        List<Trainer> trainers =  trainerDAO.getAll();
        log.info("Selection succeed");
        return trainers;
    }
}
