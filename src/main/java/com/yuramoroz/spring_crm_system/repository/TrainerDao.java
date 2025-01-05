package com.yuramoroz.spring_crm_system.repository;

import com.yuramoroz.spring_crm_system.entity.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TrainerDao implements BaseDao<Trainer> {

    private final Map<Long, Trainer> trainerStorage;

    @Autowired
    public TrainerDao(Map<Long, Trainer> trainerStorage) {
        this.trainerStorage = trainerStorage;
    }

    @Override
    public Trainer create(Trainer trainer) {
        trainerStorage.put(trainer.getId(), trainer);
        return trainer;
    }

    @Override
    public Trainer update(long id) {
        Trainer trainer = trainerStorage.get(id);
        trainerStorage.put(id, trainer);
        return trainer;
    }

    @Override
    public Trainer getById(long id) {
        return trainerStorage.get(id);
    }

    @Override
    public List<Trainer> getAll() {return new ArrayList<>(trainerStorage.values());}

    @Override
    public void delete(Trainer trainer) {}

}
