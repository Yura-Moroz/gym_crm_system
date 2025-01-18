package com.yuramoroz.spring_crm_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trainings", schema = "my_db")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_id")
    private Long id;

    @OneToMany
    @Column(name = "trainees_list")
    private List<Trainee> trainees;

    @OneToMany
    @Column(name = "trainers_list")
    private List<Trainer> trainers;

    @Column(name = "training_name", nullable = false)
    private String trainingName;

    @Column(name = "training_type")
    @Enumerated(EnumType.STRING)
    private TrainingType trainingType;

    @Column(name = "training_date", nullable = false)
    private LocalDateTime trainingDate;

    @Column(name = "training_duration", nullable = false)
    private Duration trainingDuration;

}
