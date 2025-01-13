package com.yuramoroz.spring_crm_system.repository;

import com.yuramoroz.spring_crm_system.entity.Training;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TrainingDao implements BaseDao<Training> {

    private static long trainingIDs = 0;

    private final Map<Long, Training> trainingStorage;

    @Autowired
    public TrainingDao(Map<Long, Training> trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    @Override
    public Training create(Training training) {
        training.setId(++trainingIDs);
        trainingStorage.put(training.getId(), training);
        return training;
    }

    @Override
    public Training getById(long id) {
        return trainingStorage.get(id);
    }

    @Override
    public List<Training> getAll() {
        return new ArrayList<>(trainingStorage.values());
    }

    @Override
    public Training update(Training training) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(Training training) {
        throw new NotImplementedException();
    }

}
