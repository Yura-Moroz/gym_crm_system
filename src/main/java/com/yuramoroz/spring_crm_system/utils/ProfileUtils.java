package com.yuramoroz.spring_crm_system.utils;

import com.yuramoroz.spring_crm_system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.function.Function;

@Slf4j
public class ProfileUtils {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String hashPassword(String password) {
        log.info("Hashing provided password");
        return passwordEncoder.encode(password);
    }

    public static boolean ifPasswordMatches(String password, String encodedPassword) {
        log.info("Checking if provided password matches to encoded DB version");
        return passwordEncoder.matches(password, encodedPassword);
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
