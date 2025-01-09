package com.yuramoroz.spring_crm_system.utils;

import com.yuramoroz.spring_crm_system.entity.Trainee;
import com.yuramoroz.spring_crm_system.entity.Trainer;
import com.yuramoroz.spring_crm_system.entity.User;
import com.yuramoroz.spring_crm_system.repository.TraineeDao;
import com.yuramoroz.spring_crm_system.repository.TrainerDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileLoginAndPasswordGeneratorTest {

    @Mock
    private TraineeDao traineeDao;

    @Mock
    private TrainerDao trainerDao;

    @InjectMocks
    private ProfileLoginAndPasswordGenerator generator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGeneratePassword() {
        String password = generator.generatePassword();
        assertNotNull(password);
        assertEquals(10, password.length(), "Password length should be 10 characters.");
    }

    @Test
    void testGenerateUsernameForTrainee_NoConflict() {
        Trainee newTrainee = new Trainee("John", "Doe", true, "123 Main St", null);
        when(traineeDao.getAll()).thenReturn(List.of());

        String username = generator.generateUsername(newTrainee);

        assertEquals("John.Doe", username);
        verify(traineeDao, times(1)).getAll();
    }

    @Test
    void testGenerateUsernameForTrainee_WithConflict() {
        Trainee existingTrainee = new Trainee("John", "Doe", true, "123 Main St", null);
        existingTrainee.setUserName("John.Doe");
        when(traineeDao.getAll()).thenReturn(List.of(existingTrainee));

        Trainee newTrainee = new Trainee("John", "Doe", true, "456 Elm St", null);
        String username = generator.generateUsername(newTrainee);

        assertEquals("John.Doe1", username);
        verify(traineeDao, times(1)).getAll();
    }

    @Test
    void testGenerateUsernameForTrainer_NoConflict() {
        Trainer newTrainer = new Trainer("Jane", "Smith", true, "Laughing");
        when(trainerDao.getAll()).thenReturn(List.of());

        String username = generator.generateUsername(newTrainer);

        assertEquals("Jane.Smith", username);
        verify(trainerDao, times(1)).getAll();
    }

    @Test
    void testGenerateUsernameForTrainer_WithConflict() {
        Trainer existingTrainer = new Trainer("Jane", "Smith", true, "Climbing");
        existingTrainer.setUserName("Jane.Smith");
        when(trainerDao.getAll()).thenReturn(List.of(existingTrainer));

        Trainer newTrainer = new Trainer("Jane", "Smith", true, "Swimming");
        String username = generator.generateUsername(newTrainer);

        assertEquals("Jane.Smith1", username);
        verify(trainerDao, times(1)).getAll();
    }

    @Test
    void testGenerateUsername_UnsupportedUserType() {
        User unsupportedUser = mock(User.class);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            generator.generateUsername(unsupportedUser);
        });

        assertEquals("Unsupported user type", exception.getMessage());
    }
}
