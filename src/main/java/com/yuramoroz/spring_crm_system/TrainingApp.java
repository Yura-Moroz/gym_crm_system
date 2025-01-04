package com.yuramoroz.spring_crm_system;

import com.yuramoroz.spring_crm_system.config.AppConfig;
import com.yuramoroz.spring_crm_system.service.TraineeService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;


public class TrainingApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TraineeService traineeService = context.getBean("traineeService", TraineeService.class);
        traineeService.createTrainee("Peter", "Tarantino", true, "Lviv", LocalDate.of(1999, 8, 29));
    }
}
