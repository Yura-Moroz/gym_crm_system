package com.yuramoroz.spring_crm_system;

import com.yuramoroz.spring_crm_system.repository.TrainerDAO;
import com.yuramoroz.spring_crm_system.entity.Trainer;
import com.yuramoroz.spring_crm_system.service.TrainerService;
import com.yuramoroz.spring_crm_system.utils.ProfileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class TrainerServiceTest {
    @Mock
    private TrainerDAO trainerDAO;

    @InjectMocks
    private TrainerService trainerService;

    private Trainer trainer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        trainer = new Trainer("Jason", "Statham", true, "Martial Arts");
    }

    @Test
    public void createTrainerTest_WithSingleParameter() {
        when(trainerDAO.create(any(Trainer.class))).thenReturn(trainer);

        Trainer createdTrainer = trainerService.createTrainer(trainer);

        verify(trainerDAO, times(1)).create(trainer);
        assertEquals(createdTrainer, trainer);
    }

    @Test
    public void createTrainerTest_WithSeveralParams() {
        String password = "qwerty";
        String login = "user";

        try (MockedStatic<ProfileHandler> mockedStatic = mockStatic(ProfileHandler.class)) {
            mockedStatic.when(ProfileHandler::generatePassword).thenReturn(password);
            mockedStatic.when(() -> ProfileHandler.generateUsername(any(Trainer.class))).thenReturn(login);

            when(trainerDAO.create(any(Trainer.class))).thenReturn(trainer);

            Trainer createdTrainer = trainerService.createTrainer(
                    "Jason", "Momoa", true, "Beer");

            verify(trainerDAO, times(1)).create(any(Trainer.class));
            assertEquals(password, createdTrainer.getPassword());
            assertEquals(login, createdTrainer.getUserName());
            assertEquals("Jason", createdTrainer.getFirstName());
            assertEquals("Momoa", createdTrainer.getLastName());
            assertTrue(createdTrainer.getIsActive());
            assertEquals("Beer", createdTrainer.getSpecialization());
        }
    }

    @Test
    public void updateTrainerTest(){
        when(trainerDAO.update(eq(1L))).thenReturn(trainer);

        Trainer updatedTrainer = trainerService.updateTrainer(1L);

        verify(trainerDAO, times(1)).update(1L);
        assertEquals(updatedTrainer, trainer);
    }

    @Test
    public void getTrainerByIdTest() {
        when(trainerDAO.getById(1L)).thenReturn(trainer);

        Trainer found = trainerService.getTrainerById(1L);

        verify(trainerDAO, times(1)).getById(1L);
        assertEquals(trainer, found);
    }

    @Test
    public void getAllTrainersTest() {
        List<Trainer> trainers = List.of(
                new Trainer("Johnny", "Depp", true, "Running"),
                new Trainer("Freddie", "Mercury", true, "Singing"));

        when(trainerDAO.getAllItems()).thenReturn(trainers);

        List<Trainer> resultList = trainerService.getAllTrainers();
        verify(trainerDAO, times(1)).getAllItems();
        assertEquals(resultList, trainers);
    }
}
