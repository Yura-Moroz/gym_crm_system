package com.yuramoroz.spring_crm_system;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yuramoroz.spring_crm_system.entity.TrainingType;
import com.yuramoroz.spring_crm_system.service.TraineeService;
import com.yuramoroz.spring_crm_system.service.TrainerService;
import com.yuramoroz.spring_crm_system.service.TrainingService;
import com.yuramoroz.spring_crm_system.storage.StorageInitializer;
import com.yuramoroz.spring_crm_system.entity.Trainee;
import com.yuramoroz.spring_crm_system.entity.Trainer;
import com.yuramoroz.spring_crm_system.entity.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StorageInitializerTest {
    private static final String TRAINEE_STORAGE_PATH = "src/main/resources/trainee-data.json";
    private static final String TRAINER_STORAGE_PATH = "src/main/resources/trainer-data.json";
    private static final String TRAINING_STORAGE_PATH = "src/main/resources/training-data.json";

    @Mock
    private TraineeService traineeService;
    @Mock
    private TrainerService trainerService;
    @Mock
    private TrainingService trainingService;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private StorageInitializer storageInitializer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        storageInitializer = new StorageInitializer(traineeService, trainerService, trainingService,
                TRAINEE_STORAGE_PATH, TRAINER_STORAGE_PATH, TRAINING_STORAGE_PATH, mapper);
    }

    private Object invokePrivateMethod(String methodName, Class<?>[] argClasses, Object[] argObjects) throws Exception {
        Method method = StorageInitializer.class.getDeclaredMethod(methodName, argClasses);
        method.setAccessible(true);
        return method.invoke(storageInitializer, argObjects);
    }

    @Test
    void initializeTraineeStorage_ShouldPopulateTraineeMapTest() throws Exception {
        List<Trainee> mockTrainees = List.of(
                new Trainee("Alex", "Bronco", true, "Kyiv", LocalDate.of(2001, 7, 26)),
                new Trainee("Ronnie", "Coleman", true, "USA", LocalDate.of(1964, 5, 13)),
                new Trainee("Jay", "Cutler", true, "USA", LocalDate.of(1973, 8, 3))
        );

        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(mockTrainees);

        invokePrivateMethod("initializeTraineeStorage", new Class[]{}, new Object[]{});

        verify(traineeService, times(3)).createTrainee(any(Trainee.class));
    }


    @Test
    void initializeTrainerStorage_ShouldPopulateTrainerMapTest() throws Exception {
        List<Trainer> mockTrainers = List.of(
                new Trainer("John", "Doe", true, "Fitness"),
                new Trainer("Jane", "Smith", true, "Yoga")
        );

        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(mockTrainers);

        invokePrivateMethod("initializeTrainerStorage", new Class[]{}, new Object[]{});

        verify(trainerService, times(2)).createTrainer(any(Trainer.class));
    }

    @Test
    void initializeTrainingStorage_ShouldPopulateTrainingMap() throws Exception {
        List<Training> mockTrainings = List.of(
                new Training(1L, 10L, 24L, "Full-body workout", TrainingType.CROSSFIT, LocalDateTime.of(2025, 4, 12, 14, 15, 0), Duration.ofMinutes(60)),
                new Training(2L, 2L, 31L, "Legs", TrainingType.LEGS_DAY, LocalDateTime.of(2025, 1, 15, 10, 0, 0), Duration.ofMinutes(60))
        );

        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(mockTrainings);

        invokePrivateMethod("initializeTrainingStorage", new Class[]{}, new Object[]{});

        verify(trainingService, times(2)).createTraining(any(Training.class));
    }

    @Test
    void initializeTrainingStorage_ShouldThrowAnExceptionWhenFilePathIsNull() throws NoSuchMethodException {
        StorageInitializer initializer = new StorageInitializer(traineeService, trainerService, trainingService,
                TRAINEE_STORAGE_PATH, TRAINER_STORAGE_PATH, null, mapper);

        Method method = StorageInitializer.class.getDeclaredMethod("initializeTrainingStorage", new Class[]{});
        method.setAccessible(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            try {
                method.invoke(initializer);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
        assertEquals("No proper data for Training file path was provided", exception.getMessage());
    }

    @Test
    void initializeTrainingStorage_ShouldThrowAnExceptionWhenFilePathIsBlank() throws NoSuchMethodException {
        StorageInitializer initializer = new StorageInitializer(traineeService, trainerService, trainingService,
                TRAINEE_STORAGE_PATH, TRAINER_STORAGE_PATH, "   ", mapper);

        Method method = StorageInitializer.class.getDeclaredMethod("initializeTrainingStorage", new Class[]{});
        method.setAccessible(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            try {
                method.invoke(initializer);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
        assertEquals("No proper data for Training file path was provided", exception.getMessage());
    }

    @Test
    void initializeTraineeStorage_ShouldThrowAnExceptionWhenFilePathIsNull() throws NoSuchMethodException {
        StorageInitializer initializer = new StorageInitializer(traineeService, trainerService, trainingService,
                null, TRAINER_STORAGE_PATH, TRAINING_STORAGE_PATH, mapper);

        Method method = StorageInitializer.class.getDeclaredMethod("initializeTraineeStorage", new Class[]{});
        method.setAccessible(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            try {
                method.invoke(initializer);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
        assertEquals("No proper data for Trainee file path was provided", exception.getMessage());
    }

    @Test
    void initializeTraineeStorage_ShouldThrowAnExceptionWhenFilePathIsBlank() throws NoSuchMethodException {
        StorageInitializer initializer = new StorageInitializer(traineeService, trainerService, trainingService,
                "   ", TRAINER_STORAGE_PATH, TRAINING_STORAGE_PATH, mapper);

        Method method = StorageInitializer.class.getDeclaredMethod("initializeTraineeStorage", new Class[]{});
        method.setAccessible(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            try {
                method.invoke(initializer);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
        assertEquals("No proper data for Trainee file path was provided", exception.getMessage());
    }

    @Test
    void initializeTrainerStorage_ShouldThrowAnExceptionWhenFilePathIsNull() throws NoSuchMethodException {
        StorageInitializer initializer = new StorageInitializer(traineeService, trainerService, trainingService,
                TRAINEE_STORAGE_PATH, null, TRAINING_STORAGE_PATH, mapper);

        Method method = StorageInitializer.class.getDeclaredMethod("initializeTrainerStorage", new Class[]{});
        method.setAccessible(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            try {
                method.invoke(initializer);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
        assertEquals("No proper data for Trainer file path was provided", exception.getMessage());
    }

    @Test
    void initializeTrainerStorage_ShouldThrowAnExceptionWhenFilePathIsBlank() throws NoSuchMethodException {
        StorageInitializer initializer = new StorageInitializer(traineeService, trainerService, trainingService,
                TRAINEE_STORAGE_PATH, "   ", TRAINING_STORAGE_PATH, mapper);

        Method method = StorageInitializer.class.getDeclaredMethod("initializeTrainerStorage", new Class[]{});
        method.setAccessible(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            try {
                method.invoke(initializer);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
        assertEquals("No proper data for Trainer file path was provided", exception.getMessage());
    }

    @Test
    void initializeTrainingStorage_ShouldThrowIOExceptionOnReadValueTroubles() throws IOException, NoSuchMethodException {
        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenThrow(new IOException("Mocked IOException"));

        Method method = StorageInitializer.class.getDeclaredMethod("initializeTrainingStorage", new Class[]{});
        method.setAccessible(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            try {
                method.invoke(storageInitializer);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getCause().getMessage().contains("Mocked IOException"));
    }

    @Test
    void initializeTraineeStorage_ShouldThrowIOExceptionOnReadValueTroubles() throws IOException, NoSuchMethodException {
        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenThrow(new IOException("Mocked IOException"));

        Method method = StorageInitializer.class.getDeclaredMethod("initializeTraineeStorage", new Class[]{});
        method.setAccessible(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            try {
                method.invoke(storageInitializer);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("Mocked IOException"));
    }

    @Test
    void initializeTrainerStorage_ShouldThrowIOExceptionOnReadValueTroubles() throws IOException, NoSuchMethodException {
        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenThrow(new IOException("Mocked IOException"));

        Method method = StorageInitializer.class.getDeclaredMethod("initializeTrainingStorage", new Class[]{});
        method.setAccessible(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            try {
                method.invoke(storageInitializer);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("Mocked IOException"));
    }
}
