package com.yuramoroz.spring_crm_system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Trainee extends User{
    private long id;
    private String address;
    @JsonProperty("dateOfBirth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;

    public Trainee(String firstName, String lastName, boolean isActive, String address, LocalDate dateOfBirth) {
        super(firstName, lastName, isActive);
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }
}
