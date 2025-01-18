package com.yuramoroz.spring_crm_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "trainees", schema = "my_db")
public class Trainee extends User {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "address")
    private String address;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Training> trainings = new ArrayList<>();

    public Trainee(String firstName, String lastName, Boolean isActive, String address, LocalDate dateOfBirth) {
        super(firstName, lastName, isActive);
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }
}
