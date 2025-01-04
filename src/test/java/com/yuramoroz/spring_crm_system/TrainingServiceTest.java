package com.yuramoroz.spring_crm_system;

import com.yuramoroz.spring_crm_system.repository.TrainingDAO;
import com.yuramoroz.spring_crm_system.entity.Training;
import com.yuramoroz.spring_crm_system.entity.TrainingTypeName;
import com.yuramoroz.spring_crm_system.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class TrainingServiceTest {
    @Mock
    private TrainingDAO trainingDAO;

    @InjectMocks
    private TrainingService trainingService;

    private Training training;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        training = new Training(44L, 3L, "Chest",
                TrainingTypeName.BENCH_TRAINING, LocalDateTime.of(2025, Month.AUGUST, 27, 12, 0, 0), Duration.ofMinutes(90));
    }

    @Test
    public void createTrainingTest_WithSingleParam(){
        when(trainingDAO.create(any(Training.class))).thenReturn(training);

        Training createdTraining = trainingService.createTraining(training);

        verify(trainingDAO, times(1)).create(training);
        assertEquals(createdTraining, training);
    }

    @Test
    public void createTrainingTest_WithSeveralParams(){
        when(trainingDAO.create(any(Training.class))).thenReturn(training);

        Training createdTraining = trainingService.createTraining(
                44L, 3L, "Chest", TrainingTypeName.BENCH_TRAINING,
                LocalDateTime.of(2025, Month.AUGUST, 27, 12, 0, 0),
                Duration.ofMinutes(90));

        assertEquals(44L, createdTraining.getTraineeId());
        assertEquals(3L, createdTraining.getTrainerId());
        assertEquals("Chest", createdTraining.getTrainingName());
        assertEquals(TrainingTypeName.BENCH_TRAINING, createdTraining.getTrainingType());
        assertEquals(LocalDateTime.of(2025, Month.AUGUST, 27, 12, 0, 0),
                createdTraining.getTrainingDate());
        assertEquals(Duration.ofMinutes(90), createdTraining.getTrainingDuration());
    }

    @Test
    public void getTrainingByIdTest(){
        when(trainingDAO.getById(1L)).thenReturn(training);

        Training result = trainingService.getTrainingById(1L);

        verify(trainingDAO, times(1)).getById(1L);
        assertEquals(training, result);
    }

    @Test
    public void getAllTrainingsTest(){
        List<Training> trainings = List.of(training);
        when(trainingDAO.getAllItems()).thenReturn(trainings);

        List<Training> resultList = trainingService.getAllTrainings();

        verify(trainingDAO, times(1)).getAllItems();
        assertEquals(resultList, trainings);
    }
}
