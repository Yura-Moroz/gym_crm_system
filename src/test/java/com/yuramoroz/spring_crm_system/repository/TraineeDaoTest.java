package com.yuramoroz.spring_crm_system.repository;

import com.yuramoroz.spring_crm_system.entity.Trainee;
import com.yuramoroz.spring_crm_system.repository.TraineeDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TraineeDaoTest {
    private Map<Long, Trainee> traineeStorageMock;
    private TraineeDao traineeDao;

    @BeforeEach
    void setUp() {
        traineeStorageMock = Mockito.mock(Map.class);
        traineeDao = new TraineeDao(traineeStorageMock);
    }

    @Test
    void getById_shouldReturnTraineeWhenIdExists() {
        Trainee trainee = new Trainee("Alex", "Bronco", true,
                "Kyiv", LocalDate.of(2001, 7, 26));
        when(traineeStorageMock.get(1L)).thenReturn(trainee);

        Trainee result = traineeDao.getById(1L);

        assertNotNull(result);
        assertEquals(trainee, result);
        verify(traineeStorageMock, times(1)).get(1L);
    }

    @Test
    void getById_shouldReturnNullWhenIdDoesNotExist() {
        when(traineeStorageMock.get(1L)).thenReturn(null);

        Trainee result = traineeDao.getById(1L);

        assertNull(result);
        verify(traineeStorageMock, times(1)).get(1L);
    }

    @Test
    void getAll_shouldReturnListOfTrainees() {
        List<Trainee> trainees = List.of(
                new Trainee("Alex", "Bronco", true,
                        "Kyiv", LocalDate.of(2001, 7, 26)),
                new Trainee("Ronnie", "Coleman", true, "USA",
                        LocalDate.of(1964, 5, 13)));
        when(traineeStorageMock.values()).thenReturn(new ArrayList<>(trainees));

        List<Trainee> result = traineeDao.getAll();

        assertNotNull(result);
        assertEquals(trainees.size(), result.size());
        assertTrue(result.containsAll(trainees));
        verify(traineeStorageMock, times(1)).values();
    }

    @Test
    void create_shouldAddTraineeToStorage() {
        Trainee trainee = new Trainee("Alex", "Bronco", true,
                "Kyiv", LocalDate.of(2001, 7, 26));

        Trainee result = traineeDao.create(trainee);

        assertNotNull(result);
        assertEquals(trainee, result);
        verify(traineeStorageMock, times(1)).put(trainee.getId(), trainee);
    }

    @Test
    void update_shouldUpdateTraineeInStorage() {
        Trainee existingTrainee = new Trainee("Alex", "Bronco", true,
                "Kyiv", LocalDate.of(2001, 7, 26));

        Trainee result = traineeDao.update(existingTrainee);

        assertNotNull(result);
        assertEquals(existingTrainee, result);
        verify(traineeStorageMock, times(1)).put(existingTrainee.getId(), existingTrainee);
    }

    @Test
    void delete_shouldRemoveTraineeFromStorage() {
        Trainee trainee = new Trainee("Alex", "Bronco", true,
                "Kyiv", LocalDate.of(2001, 7, 26));

        traineeDao.delete(trainee);

        verify(traineeStorageMock, times(1)).remove(trainee.getId());
    }
}
