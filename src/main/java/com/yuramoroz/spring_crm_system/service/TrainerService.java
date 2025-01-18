package com.yuramoroz.spring_crm_system.service;

import com.yuramoroz.spring_crm_system.entity.Trainer;
import com.yuramoroz.spring_crm_system.repository.impl.TrainerDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TrainerService extends BaseUserService<Trainer>{
    @Autowired
    private final TrainerDao trainerDao;

    public TrainerService(TrainerDao trainerDao) {
        super(trainerDao);
        this.trainerDao = trainerDao;
    }

    public Trainer saveTrainer(String firstName, String lastName, String password, String specialization) {
        log.info("Trying to create and save {} {} trainer...", firstName, lastName);

        Trainer trainer = Trainer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .specialization(specialization)
                .build();

        trainer = super.saveUser(trainer);

        log.info("The trainer {} {} was created successfully", firstName, lastName);
        return trainer;
    }

}
