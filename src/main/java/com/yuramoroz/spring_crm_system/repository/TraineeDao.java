package com.yuramoroz.spring_crm_system.repository;

import com.yuramoroz.spring_crm_system.entity.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TraineeDao implements BaseDao<Trainee> {
    private static long traineeIDs = 0L;

    private final Map<Long, Trainee> traineeStorage;

    @Autowired
    public TraineeDao(Map<Long, Trainee> traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    @Override
    public Trainee getById(long id) {
        return traineeStorage.get(id);
    }

    @Override
    public List<Trainee> getAll() {return new ArrayList<>(traineeStorage.values());}

    @Override
    public Trainee create(Trainee trainee) {
        trainee.setId(++traineeIDs);
        traineeStorage.put(trainee.getId(), trainee);
        return trainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        traineeStorage.put(trainee.getId(), trainee);
        return trainee;
    }

    @Override
    public void delete(Trainee trainee) {
        traineeStorage.remove(trainee.getId());
    }
}
