package com.yuramoroz.spring_crm_system;

import com.yuramoroz.spring_crm_system.entity.Trainer;
import com.yuramoroz.spring_crm_system.repository.TrainerDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerDaoTest {

    private Map<Long, Trainer> trainerStorageMock;
    private TrainerDao trainerDao;

    @BeforeEach
    void setUp() {
        trainerStorageMock = Mockito.mock(Map.class);
        trainerDao = new TrainerDao(trainerStorageMock);
    }

    @Test
    void create_shouldAddTrainerToStorage() {
        Trainer trainer = new Trainer(1L, "Alice Johnson");

        Trainer result = trainerDao.create(trainer);

        assertNotNull(result);
        assertEquals(trainer, result);
        verify(trainerStorageMock, times(1)).put(trainer.getId(), trainer);
    }

    @Test
    void update_shouldUpdateTrainerInStorage() {
        Trainer trainer = new Trainer(1L, "Alice Johnson");
        when(trainerStorageMock.get(1L)).thenReturn(trainer);

        Trainer result = trainerDao.update(1L);

        assertNotNull(result);
        assertEquals(trainer, result);
        verify(trainerStorageMock, times(1)).get(1L);
        verify(trainerStorageMock, times(1)).put(1L, trainer);
    }

    @Test
    void getById_shouldReturnTrainerWhenIdExists() {
        Trainer trainer = new Trainer(1L, "Alice Johnson");
        when(trainerStorageMock.get(1L)).thenReturn(trainer);

        Trainer result = trainerDao.getById(1L);

        assertNotNull(result);
        assertEquals(trainer, result);
        verify(trainerStorageMock, times(1)).get(1L);
    }

    @Test
    void getById_shouldReturnNullWhenIdDoesNotExist() {
        when(trainerStorageMock.get(1L)).thenReturn(null);

        Trainer result = trainerDao.getById(1L);

        assertNull(result);
        verify(trainerStorageMock, times(1)).get(1L);
    }

    @Test
    void getAll_shouldReturnListOfTrainers() {
        List<Trainer> trainers = List.of(
                new Trainer(1L, "Alice Johnson"),
                new Trainer(2L, "Bob Smith")
        );
        when(trainerStorageMock.values()).thenReturn(new ArrayList<>(trainers));

        List<Trainer> result = trainerDao.getAll();

        assertNotNull(result);
        assertEquals(trainers.size(), result.size());
        assertTrue(result.containsAll(trainers));
        verify(trainerStorageMock, times(1)).values();
    }
}
