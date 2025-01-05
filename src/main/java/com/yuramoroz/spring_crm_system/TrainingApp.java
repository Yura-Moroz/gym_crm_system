package com.yuramoroz.spring_crm_system;

import com.yuramoroz.spring_crm_system.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TrainingApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
