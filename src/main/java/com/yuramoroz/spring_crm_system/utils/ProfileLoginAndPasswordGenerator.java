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
import java.util.function.Function;

@Slf4j
public class ProfileLoginAndPasswordGenerator {
    public static String generatePassword() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z').build();
        log.info("Generated unique username for new user");
        return generator.generate(10);
    }

    public static String generateUsername(User user, Function<String, Boolean> userExistenceChecker) {
        String baseUsername = user.getFirstName() + "." + user.getLastName();
        String username = baseUsername;
        int serialNumber = 1;

        while (userExistenceChecker.apply(username)) {
            username = baseUsername + serialNumber;
            serialNumber++;
        }

        log.info("Generated unique username for: {} {}", user.getFirstName(), user.getLastName());
        return username;
    }
}
