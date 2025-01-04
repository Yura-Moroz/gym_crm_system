package com.yuramoroz.spring_crm_system;

import com.yuramoroz.spring_crm_system.repository.TraineeDAO;
import com.yuramoroz.spring_crm_system.entity.Trainee;
import com.yuramoroz.spring_crm_system.service.TraineeService;
import com.yuramoroz.spring_crm_system.utils.ProfileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TraineeServiceTest {
    @Mock
    private TraineeDAO traineeDAO;

    @InjectMocks
    private TraineeService traineeService;

    private Trainee trainee;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        trainee = new Trainee("Peter", "Tarantino", true, "Lviv", LocalDate.of(1999, 8, 29));
    }

    @Test
    public void createTraineeTest_WithSingleParameter() {

        when(traineeDAO.create(any(Trainee.class))).thenReturn(trainee);

        Trainee createdTrainee = traineeService.createTrainee(trainee);

        verify(traineeDAO, times(1)).create(trainee);
        assertEquals(trainee, createdTrainee);
    }

    @Test
    public void createTraineeTest_WithPlentyOfParameters() {
        String password = "qwerty";
        String login = "user";

        try (MockedStatic<ProfileHandler> mockedStatic = mockStatic(ProfileHandler.class)) {
            mockedStatic.when(ProfileHandler::generatePassword).thenReturn(password);
            mockedStatic.when(() -> ProfileHandler.generateUsername(any(Trainee.class))).thenReturn(login);

            when(traineeDAO.create(any(Trainee.class))).thenReturn(trainee);

            Trainee createdTrainee = traineeService.createTrainee(
                    "Peter", "Pranker", true, "Washington", LocalDate.of(1990, 10, 1));

            verify(traineeDAO, times(1)).create(any(Trainee.class));
            assertEquals(password, createdTrainee.getPassword());
            assertEquals(login, createdTrainee.getUserName());
            assertEquals("Peter", createdTrainee.getFirstName());
            assertEquals("Pranker", createdTrainee.getLastName());
            assertTrue(createdTrainee.getIsActive());
            assertEquals("Washington", createdTrainee.getAddress());
            assertEquals(LocalDate.of(1990, 10, 1), createdTrainee.getDateOfBirth());
        }
    }

    @Test
    public void updateTraineeTest() {

        when(traineeDAO.update(eq(1L))).thenReturn(trainee);

        Trainee updatedTrainee = traineeService.updateTrainee(1L);

        verify(traineeDAO, times(1)).update(1L);

        assertEquals(updatedTrainee, trainee);
    }

    @Test
    public void deleteTraineeTest() {
        traineeService.deleteTrainee(trainee);
        verify(traineeDAO, times(1)).delete(trainee);
    }

    @Test
    public void getTraineeByIdTest() {
        when(traineeDAO.getById(eq(1L))).thenReturn(trainee);

        Trainee found = traineeService.getTraineeById(1L);

        verify(traineeDAO, times(1)).getById(1L);
        assertEquals(trainee, found);
    }

    @Test
    public void getAllTraineesTest() {
        List<Trainee> trainees = List.of(
                new Trainee("Jim", "Ivanov", false, "Iowa", LocalDate.of(1988, 11, 19)),
                new Trainee("Tom", "Cruise", true, "NYC", LocalDate.of(1962, 7, 3)));
        when(traineeDAO.getAllItems()).thenReturn(trainees);

        List<Trainee> resultList = traineeService.getAllTrainees();
        verify(traineeDAO, times(1)).getAllItems();
        assertEquals(resultList, trainees);
    }
}
