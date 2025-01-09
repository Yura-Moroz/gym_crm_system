package com.yuramoroz.spring_crm_system.service;

import com.yuramoroz.spring_crm_system.repository.TrainingDao;
import com.yuramoroz.spring_crm_system.entity.Training;
import com.yuramoroz.spring_crm_system.entity.TrainingType;
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
    private TrainingDao trainingDAO;

    @InjectMocks
    private TrainingService trainingService;

    private Training training;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        training = new Training(44L, 3L, "Chest",
                TrainingType.BENCH_TRAINING, LocalDateTime.of(2025, Month.AUGUST, 27, 12, 0, 0), Duration.ofMinutes(90));
    }

    @Test
    public void createTrainingTest(){
        when(trainingDAO.create(any(Training.class))).thenReturn(training);

        Training createdTraining = trainingService.createTraining(training);

        verify(trainingDAO, times(1)).create(training);
        assertEquals(createdTraining, training);
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
        when(trainingDAO.getAll()).thenReturn(trainings);

        List<Training> resultList = trainingService.getAllTrainings();

        verify(trainingDAO, times(1)).getAll();
        assertEquals(resultList, trainings);
    }
}
