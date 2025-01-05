package com.yuramoroz.spring_crm_system;

import com.yuramoroz.spring_crm_system.entity.Training;
import com.yuramoroz.spring_crm_system.entity.TrainingType;
import com.yuramoroz.spring_crm_system.repository.TrainingDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingDaoTest {

    private Map<Long, Training> trainingStorageMock;
    private TrainingDao trainingDao;

    @BeforeEach
    void setUp() {
        trainingStorageMock = Mockito.mock(Map.class);
        trainingDao = new TrainingDao(trainingStorageMock);
    }

    @Test
    void create_shouldAddTrainingToStorage() {
        Training training = new Training(1L, 10L, 24L, "Full-body workout", TrainingType.CROSSFIT, LocalDateTime.of(2025, 4, 12, 14, 15, 0), Duration.ofMinutes(60));

        Training result = trainingDao.create(training);

        assertNotNull(result);
        assertEquals(training, result);
        verify(trainingStorageMock, times(1)).put(training.getId(), training);
    }

    @Test
    void getById_shouldReturnTrainingWhenIdExists() {
        Training training = new Training(1L, 10L, 24L, "Full-body workout", TrainingType.CROSSFIT, LocalDateTime.of(2025, 4, 12, 14, 15, 0), Duration.ofMinutes(60));
        when(trainingStorageMock.get(1L)).thenReturn(training);

        Training result = trainingDao.getById(1L);

        assertNotNull(result);
        assertEquals(training, result);
        verify(trainingStorageMock, times(1)).get(1L);
    }

    @Test
    void getById_shouldReturnNullWhenIdDoesNotExist() {
        when(trainingStorageMock.get(1L)).thenReturn(null);

        Training result = trainingDao.getById(1L);

        assertNull(result);
        verify(trainingStorageMock, times(1)).get(1L);
    }

    @Test
    void getAll_shouldReturnListOfTrainings() {
        List<Training> trainings = List.of(
                new Training(1L, 10L, 24L, "Full-body workout",
                        TrainingType.CROSSFIT,
                        LocalDateTime.of(2025, 4, 12, 14, 15, 0),
                        Duration.ofMinutes(60)),
                new Training(2L, 2L, 31L, "Legs",
                        TrainingType.LEGS_DAY,
                        LocalDateTime.of(2025, 1, 15, 10, 0, 0),
                        Duration.ofMinutes(60)));
        when(trainingStorageMock.values()).thenReturn(new ArrayList<>(trainings));

        List<Training> result = trainingDao.getAll();

        assertNotNull(result);
        assertEquals(trainings.size(), result.size());
        assertTrue(result.containsAll(trainings));
        verify(trainingStorageMock, times(1)).values();
    }
}
