package com.yuramoroz.spring_crm_system.entity;

import com.yuramoroz.spring_crm_system.enums.TrainingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

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
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @Column(name = "trainees_list")
    private Trainee trainee;

    @ManyToOne
    @Column(name = "trainers_list")
    private Trainer trainer;

    @Column(name = "training_name", nullable = false)
    private String trainingName;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TrainingType trainingType;

    @Column(name = "date", nullable = false)
    private LocalDateTime trainingDate;

    @Column(name = "duration", nullable = false)
    private Duration trainingDuration;

}
