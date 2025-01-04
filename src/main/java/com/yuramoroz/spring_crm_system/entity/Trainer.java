package com.yuramoroz.spring_crm_system.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Trainer extends User{
    private long id;
    private String specialization;

    public Trainer(String firstName, String lastName, boolean isActive, String specialization) {
        super(firstName, lastName, isActive);
        this.specialization = specialization;
    }
}
