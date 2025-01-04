package com.yuramoroz.spring_crm_system.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class User {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private Boolean isActive;

    public User(String firstName, String lastName, Boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
    }

}
