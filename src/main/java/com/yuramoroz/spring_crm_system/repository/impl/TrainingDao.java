package com.yuramoroz.spring_crm_system.repository.impl;

import com.yuramoroz.spring_crm_system.entity.Training;
import com.yuramoroz.spring_crm_system.repository.BaseDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class TrainingDao implements BaseDao<Training> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Training> getById(long id) {
        log.info("Getting a training by id");
        Training training = entityManager.find(Training.class, id);
        return training != null ? Optional.of(training) : Optional.empty();
    }

    @Override
    public List<Training> getAll() {
        log.info("Getting a list of all trainings in the DB");
        Query query = entityManager.createQuery("from Training", Training.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Training save(Training training) {
        log.info("Trying to save a training to the DB");

        try {
            if (training.getId() == null) {
                entityManager.persist(training);
            } else {
                training = entityManager.merge(training);
            }
        } catch (Exception e) {
            log.error("Something went wrong when attempting to save the Training");
        }
        return training;
    }

    @Override
    public Training update(Training training) {
        return save(training);
    }

    @Override
    public void delete(Training training) {
        log.info("Trying to delete a training from the DB");

        if (entityManager.contains(training)) {
            entityManager.remove(training);
        }
    }

    @Override
    public boolean ifExistById(long id) {
        log.info("Checking if user exist by id");
        return entityManager.find(Training.class, id) != null;
    }

}
