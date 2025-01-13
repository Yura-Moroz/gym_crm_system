package com.yuramoroz.spring_crm_system.utils;

import com.yuramoroz.spring_crm_system.entity.User;
import org.junit.jupiter.api.Test;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileLoginAndPasswordGeneratorTest {

    @Test
    void testGeneratePassword() {
        String password = ProfileLoginAndPasswordGenerator.generatePassword();

        assertNotNull(password);

        for (char c : password.toCharArray()) {
            assertTrue(c >= '0' && c <= 'z', "Password contains invalid character: " + c);
        }
        assertEquals(10, password.length(), "Password length should be 10 characters.");
    }

    @Test
    void testGenerateUsername_NoConflicts() {
        User mockUser = mock(User.class);
        when(mockUser.getFirstName()).thenReturn("John");
        when(mockUser.getLastName()).thenReturn("Doe");

        Function<String, Boolean> userExistenceChecker = username -> false;

        String generatedUsername = ProfileLoginAndPasswordGenerator.generateUsername(mockUser, userExistenceChecker);

        assertEquals("John.Doe", generatedUsername, "Generated username should be 'John.Doe'");
    }

    @Test
    void testGenerateUsername_WithConflicts() {
        User mockUser = mock(User.class);
        when(mockUser.getFirstName()).thenReturn("Jane");
        when(mockUser.getLastName()).thenReturn("Smith");

        // Mock user existence checker: returns true for "Jane.Smith" and "Jane.Smith1", then false
        Function<String, Boolean> userExistenceChecker = mock(Function.class);
        when(userExistenceChecker.apply("Jane.Smith")).thenReturn(true);
        when(userExistenceChecker.apply("Jane.Smith1")).thenReturn(true);
        when(userExistenceChecker.apply("Jane.Smith2")).thenReturn(false);

        String generatedUsername = ProfileLoginAndPasswordGenerator.generateUsername(mockUser, userExistenceChecker);

        assertEquals("Jane.Smith2", generatedUsername, "Generated username should be 'Jane.Smith2'");
    }

    @Test
    void testGenerateUsername_EmptyNames() {
        User mockUser = mock(User.class);
        when(mockUser.getFirstName()).thenReturn("");
        when(mockUser.getLastName()).thenReturn("");

        // Mock user existence checker: always returns false (no conflicts)
        Function<String, Boolean> userExistenceChecker = username -> false;

        String generatedUsername = ProfileLoginAndPasswordGenerator.generateUsername(mockUser, userExistenceChecker);

        // Verify the username
        assertEquals(".", generatedUsername, "Generated username should be '.' for empty first and last names");
    }

}
