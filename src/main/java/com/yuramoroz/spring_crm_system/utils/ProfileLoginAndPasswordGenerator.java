package com.yuramoroz.spring_crm_system.utils;

import com.yuramoroz.spring_crm_system.entity.Trainee;
import com.yuramoroz.spring_crm_system.entity.Trainer;
import com.yuramoroz.spring_crm_system.entity.User;
import com.yuramoroz.spring_crm_system.repository.TraineeDao;
import com.yuramoroz.spring_crm_system.repository.TrainerDao;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ProfileLoginAndPasswordGenerator {
    @Autowired
    private TraineeDao traineeDao;
    @Autowired
    private TrainerDao trainerDao;

    private static final Set<String> existingUsernames = new HashSet<>();

    public String generatePassword() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z').build();
        return generator.generate(10);
    }

    public String generateUsername(User user) {
        String baseUser = user.getFirstName() + "." + user.getLastName();
        String newUsername = baseUser;

        if(user instanceof Trainee){
            for(Trainee trainee : traineeDao.getAll()){
                existingUsernames.add(trainee.getUserName());
            }
        }else if(user instanceof Trainer){
            for(Trainer trainer : trainerDao.getAll()){
                existingUsernames.add(trainer.getUserName());
            }
        }

        int serialNumber = 1;
        while (existingUsernames.contains(newUsername)){
            newUsername = baseUser + serialNumber;
            serialNumber++;
        }
        return newUsername;
    }
}
