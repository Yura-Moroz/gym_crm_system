package com.yuramoroz.spring_crm_system.service;

import com.yuramoroz.spring_crm_system.entity.User;
import com.yuramoroz.spring_crm_system.entity.Training;
import com.yuramoroz.spring_crm_system.repository.impl.UserDaoImpl;
import com.yuramoroz.spring_crm_system.utils.ProfileUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public abstract class BaseUserService<T extends User> {

    private final UserDaoImpl<T> userDao;

    public T saveUser(T user) {
        log.info("Trying to save {} {} user...", user.getFirstName(), user.getLastName());

        if (user == null) throw new IllegalArgumentException("Expected User but no proper data was provided");

        user.setUserName(ProfileUtils.generateUsername(user, userDao::ifUserExistByUsername));
        user.setPassword(ProfileUtils.hashPassword(user.getPassword()));

        T result = userDao.save(user);
        log.info("User {} {} was successfully saved to the DB", user.getFirstName(), user.getLastName());
        return result;
    }

    public User selectUserByUsername(String username) {
        log.info("Selecting User by {} username", username);

        return userDao.ifUserExistByUsername(username) ? userDao.getUserByUsername(username).get() : null;
    }

    public void changeUserPassword(User user, String oldPassword, String newPassword) {
        log.info("Trying to change password in {} {} user", user.getFirstName(), user.getLastName());

        boolean approvedPass = ProfileUtils.ifPasswordMatches(oldPassword, user.getPassword());

        if (approvedPass) {
            user.setPassword(ProfileUtils.hashPassword(newPassword));
            log.info("The new password was successfully set to {} {} user", user.getFirstName(), user.getLastName());
        } else log.info("Sorry... It seems that you've provided a wrong password...");
    }

    public T updateUser(T user) {
        log.info("Updating {} {} user", user.getFirstName(), user.getLastName());
        return userDao.update(user);
    }

    public void activateOrDeactivateUser(T user) {
        log.info("Activating/Deactivating {} {} profile", user.getFirstName(), user.getLastName());

        user.setActive(!user.isActive());
    }

    public void deleteUserByUsername(String username) {
        log.info("Trying to delete a user by {}", username);

        if (userDao.ifUserExistByUsername(username)) {
            T user = userDao.getUserByUsername(username).get();
            userDao.delete(user);
        } else {
            log.warn("There was no user found with {} username", username);
        }
    }

    public List<Training> getTrainingByUsernameAndCriteria() {
        return null;
    }
}
