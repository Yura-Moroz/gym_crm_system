package com.yuramoroz.spring_crm_system.repository.impl;

import com.yuramoroz.spring_crm_system.entity.User;
import com.yuramoroz.spring_crm_system.repository.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public abstract class UserDaoImpl<T extends User> implements UserDao<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> clazz;

    public UserDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Optional<T> getById(long id) {
        log.info("Getting user by id");

        T entity = entityManager.find(clazz, id);
        return entity != null ? Optional.of(entity) : Optional.empty();
    }

    @Override
    public List<T> getAll() {
        log.info("Getting a list of all users present in the DB");

        Query query = entityManager.createQuery("from " + clazz.getName());
        return query.getResultList();
    }

    @Override
    public boolean ifExistById(long id) {
        log.info("Checking if user exist by id");
        return entityManager.find(clazz, id) != null;
    }

    @Override
    @Transactional
    public T save(T entity) {
        log.info("Trying to save an entity to the DB");

        try {
            if (entity.getId() == null) {
                entityManager.persist(entity);
            } else {
                entity = entityManager.merge(entity);
            }
        } catch (Exception e) {
            log.error("Something went wrong when attempting to save the Trainer");
        }
        return entity;
    }

    @Override
    public T update(T entity) {
        log.info("Trying to update an entity in the DB");
        return save(entity);
    }

    @Override
    public void delete(T entity) {
        log.info("Trying to delete a user from the DB");

        if (entityManager.contains(entity)) {
            entityManager.remove(entity);
        }
    }

    @Override
    public Optional<T> getUserByUsername(String username) {
        log.info("Trying to get user by '" + username + "' login");

        String jpqlQuery = "from " + clazz.getName() + " user where user.userName = :login";
        Query query = entityManager.createQuery(jpqlQuery);
        query.setParameter("login", username);

        T returnedUser = (T) query.getSingleResult();
        return returnedUser != null ? Optional.of(returnedUser) : Optional.empty();
    }

    @Override
    public boolean ifUserExistByUsername(String username) {
        log.info("Checking if user exists with '" + username + "' login");

        String jpqlQuery = "from " + clazz.getName() + " user where user.userName = :login";
        Query query = entityManager.createQuery(jpqlQuery);
        query.setParameter("login", username);

        return query.getSingleResult() != null;
    }

}
