package com.yuramoroz.spring_crm_system.utils;

import com.yuramoroz.spring_crm_system.entity.Trainee;
import com.yuramoroz.spring_crm_system.entity.Trainer;
import com.yuramoroz.spring_crm_system.entity.User;
import com.yuramoroz.spring_crm_system.repository.TraineeDao;
import com.yuramoroz.spring_crm_system.repository.TrainerDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProfileLoginAndPasswordGenerator {
    @Autowired
    private TraineeDao traineeDao;
    @Autowired
    private TrainerDao trainerDao;

    public String generatePassword() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z').build();
        log.info("Generated unique username for new user");
        return generator.generate(10);
    }

    public String generateUsername(User user) {
        String baseUsername = user.getFirstName() + "." + user.getLastName();
        String username = baseUsername;
        int serialNumber = 1;

        List<? extends User> userList;
        if (user instanceof Trainee) {
            userList = traineeDao.getAll();
        } else if (user instanceof Trainer) {
            userList = trainerDao.getAll();
        } else {
            throw new IllegalArgumentException("Unsupported user type");
        }

        while (usernameExists(username, userList)) {
            username = baseUsername + serialNumber;
            serialNumber++;
        }

        log.info("Generated unique username for: {} {}", user.getFirstName(), user.getLastName());
        return username;
    }

    private boolean usernameExists(String usernameToCheck, List<? extends User> users) {
        return users.stream().anyMatch(user -> user.getUserName().equals(usernameToCheck));
    }
}
