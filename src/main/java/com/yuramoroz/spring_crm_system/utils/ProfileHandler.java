package com.yuramoroz.spring_crm_system.utils;

import com.yuramoroz.spring_crm_system.entity.User;
import org.apache.commons.text.RandomStringGenerator;

import java.util.HashMap;
import java.util.Map;

public class ProfileHandler {
    private static final Map<String, Integer> logins = new HashMap<>();

    public static String generatePassword() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z').build();
        return generator.generate(10);
    }

    public static String generateUsername(User user) {
        String resultLogin = user.getFirstName() + "." + user.getLastName();

        if (logins.containsKey(resultLogin)) {
            int serialNumber = logins.get(resultLogin);
            logins.put(resultLogin, serialNumber + 1);
            resultLogin += serialNumber;
        }
        logins.put(resultLogin, 0);

        return resultLogin;
    }
}
