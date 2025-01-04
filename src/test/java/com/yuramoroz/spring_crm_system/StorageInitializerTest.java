package com.yuramoroz.spring_crm_system;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yuramoroz.spring_crm_system.entity.TrainingTypeName;
import com.yuramoroz.spring_crm_system.storage.StorageInitializer;
import com.yuramoroz.spring_crm_system.entity.Trainee;
import com.yuramoroz.spring_crm_system.entity.Trainer;
import com.yuramoroz.spring_crm_system.entity.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StorageInitializerTest {
    private static final String TRAINEE_STORAGE_PATH = "src/main/resources/trainee-data.json";
    private static final String TRAINER_STORAGE_PATH = "src/main/resources/trainer-data.json";
    private static final String TRAINING_STORAGE_PATH = "src/main/resources/training-data.json";

    @Mock
    private Map<Long, Trainee> traineeStorage;

    @Mock
    private Map<Long, Trainer> trainerStorage;

    @Mock
    private Map<Long, Training> trainingStorage;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private StorageInitializer storageInitializer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        storageInitializer = new StorageInitializer(
                traineeStorage, trainerStorage, trainingStorage,
                TRAINEE_STORAGE_PATH, TRAINER_STORAGE_PATH, TRAINING_STORAGE_PATH, mapper);
    }

    @Test
    void initializeTraineeStorage_ShouldPopulateTraineeMapTest() throws IOException {
        List<Trainee> mockTrainees = List.of(new Trainee("Alex", "Bronco", true,
                        "Kyiv", LocalDate.of(2001, 7, 26)),
                new Trainee("Ronnie", "Coleman", true, "USA",
                        LocalDate.of(1964, 5, 13)),
                new Trainee("Jay", "Cutler", true, "USA",
                        LocalDate.of(1973, 8, 3)));

        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(mockTrainees);

        storageInitializer.initializeTraineeStorage();

        verify(traineeStorage, times(3)).put(anyLong(), any(Trainee.class));
    }

    @Test
    void initializeTrainerStorage_ShouldPopulateTrainerMapTest() throws Exception {
        List<Trainer> mockTrainers = List.of(
                new Trainer("John", "Doe", true, "Fitness"),
                new Trainer("Jane", "Smith", true, "Yoga")
        );

        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(mockTrainers);

        storageInitializer.initializeTrainerStorage();

        verify(trainerStorage, times(2)).put(anyLong(), any(Trainer.class));
    }

    @Test
    void initializeTrainingStorage_ShouldPopulateTrainingMap() throws Exception {
        List<Training> mockTrainings = List.of(
                new Training(1L, 10L, 24L, "Full-body workout", TrainingTypeName.CROSSFIT, LocalDateTime.of(2025, 4, 12, 14, 15, 0), Duration.ofMinutes(60)),
                new Training(2L, 2L, 31L, "Legs", TrainingTypeName.LEGS_DAY, LocalDateTime.of(2025, 1, 15, 10, 0, 0), Duration.ofMinutes(60))
        );

        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(mockTrainings);

        storageInitializer.initializeTrainingStorage();

        verify(trainingStorage, times(2)).put(anyLong(), any(Training.class));
    }

    @Test
    void initializeTrainingStorage_ShouldThrowAnExceptionWhenFilePathIsNull() {
        StorageInitializer initializer = new StorageInitializer(traineeStorage, trainerStorage, trainingStorage,
                TRAINEE_STORAGE_PATH, TRAINER_STORAGE_PATH, null, mapper);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, initializer::initializeTrainingStorage);
        assertEquals("No proper data for Training file path was provided", exception.getMessage());
    }

    @Test
    void initializeTrainingStorage_ShouldThrowAnExceptionWhenFilePathIsBlank() {
        StorageInitializer initializer = new StorageInitializer(traineeStorage, trainerStorage, trainingStorage,
                TRAINEE_STORAGE_PATH, TRAINER_STORAGE_PATH, "   ", mapper);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, initializer::initializeTrainingStorage);
        assertEquals("No proper data for Training file path was provided", exception.getMessage());
    }

    @Test
    void initializeTraineeStorage_ShouldThrowAnExceptionWhenFilePathIsNull() {
        StorageInitializer initializer = new StorageInitializer(traineeStorage, trainerStorage, trainingStorage,
                null, TRAINER_STORAGE_PATH, TRAINING_STORAGE_PATH, mapper);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, initializer::initializeTraineeStorage);
        assertEquals("No proper data for Trainee file path was provided", exception.getMessage());
    }

    @Test
    void initializeTraineeStorage_ShouldThrowAnExceptionWhenFilePathIsBlank() {
        StorageInitializer initializer = new StorageInitializer(traineeStorage, trainerStorage, trainingStorage,
                "   ", TRAINER_STORAGE_PATH, TRAINING_STORAGE_PATH, mapper);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, initializer::initializeTraineeStorage);
        assertEquals("No proper data for Trainee file path was provided", exception.getMessage());
    }

    @Test
    void initializeTrainerStorage_ShouldThrowAnExceptionWhenFilePathIsNull() {
        StorageInitializer initializer = new StorageInitializer(traineeStorage, trainerStorage, trainingStorage,
                TRAINEE_STORAGE_PATH, null, TRAINING_STORAGE_PATH, mapper);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, initializer::initializeTrainerStorage);
        assertEquals("No proper data for Trainer file path was provided", exception.getMessage());
    }

    @Test
    void initializeTrainerStorage_ShouldThrowAnExceptionWhenFilePathIsBlank() {
        StorageInitializer initializer = new StorageInitializer(traineeStorage, trainerStorage, trainingStorage,
                TRAINEE_STORAGE_PATH, "   ", TRAINING_STORAGE_PATH, mapper);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, initializer::initializeTrainerStorage);
        assertEquals("No proper data for Trainer file path was provided", exception.getMessage());
    }

    @Test
    void initializeTrainingStorage_ShouldThrowIOExceptionOnReadValueTroubles() throws IOException {
        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenThrow(new IOException("Mocked IOException"));

        RuntimeException exception = assertThrows(RuntimeException.class, storageInitializer::initializeTrainingStorage);
        assertTrue(exception.getMessage().contains("Mocked IOException"));
    }

    @Test
    void initializeTraineeStorage_ShouldThrowIOExceptionOnReadValueTroubles() throws IOException {
        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenThrow(new IOException("Mocked IOException"));

        RuntimeException exception = assertThrows(RuntimeException.class, storageInitializer::initializeTraineeStorage);
        assertTrue(exception.getMessage().contains("Mocked IOException"));
    }

    @Test
    void initializeTrainerStorage_ShouldThrowIOExceptionOnReadValueTroubles() throws IOException {
        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenThrow(new IOException("Mocked IOException"));

        RuntimeException exception = assertThrows(RuntimeException.class, storageInitializer::initializeTrainerStorage);
        assertTrue(exception.getMessage().contains("Mocked IOException"));
    }
}
