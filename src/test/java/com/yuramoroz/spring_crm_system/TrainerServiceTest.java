package com.yuramoroz.spring_crm_system;

import com.yuramoroz.spring_crm_system.repository.TrainerDao;
import com.yuramoroz.spring_crm_system.entity.Trainer;
import com.yuramoroz.spring_crm_system.service.TrainerService;
import com.yuramoroz.spring_crm_system.utils.ProfileLoginAndPasswordGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class TrainerServiceTest {
    @Mock
    private TrainerDao trainerDAO;

    @Mock
    private ProfileLoginAndPasswordGenerator loginAndPasswordGenerator;

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

        when(loginAndPasswordGenerator.generatePassword()).thenReturn(password);
        when(loginAndPasswordGenerator.generateUsername(any(Trainer.class))).thenReturn(login);
        when(trainerDAO.create(any(Trainer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Trainer createdTrainer = trainerService.createTrainer(
                "Jason", "Momoa", true, "Beer");

        verify(trainerDAO, times(1)).create(any(Trainer.class));
        assertEquals(password, createdTrainer.getPassword());
        assertEquals(login, createdTrainer.getUserName());
        assertEquals("Jason", createdTrainer.getFirstName());
        assertEquals("Momoa", createdTrainer.getLastName());
        assertTrue(createdTrainer.getActive());
        assertEquals("Beer", createdTrainer.getSpecialization());
    }

    @Test
    public void updateTrainerTest() {

        when(trainerDAO.getById(trainer.getId())).thenReturn(trainer);
        when(trainerDAO.update(any(Trainer.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        trainerService.createTrainer(trainer);

        Trainer updatedTrainer = trainerService.getTrainerById(trainer.getId());
        updatedTrainer.setSpecialization("Jumping");
        updatedTrainer.setActive(false);

        trainerService.updateTrainer(updatedTrainer);
        updatedTrainer = trainerService.getTrainerById(updatedTrainer.getId());

        verify(trainerDAO, times(2)).getById(updatedTrainer.getId());
        verify(trainerDAO, times(1)).update(updatedTrainer);
        verify(trainerDAO, times(1)).create(trainer);
        assertEquals("Jason", updatedTrainer.getFirstName());
        assertEquals("Statham", trainer.getLastName());
        assertEquals("Jumping", trainer.getSpecialization());
        assertEquals(false, trainer.getActive());
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

        when(trainerDAO.getAll()).thenReturn(trainers);

        List<Trainer> resultList = trainerService.getAllTrainers();
        verify(trainerDAO, times(1)).getAll();
        assertEquals(resultList, trainers);
    }
}
