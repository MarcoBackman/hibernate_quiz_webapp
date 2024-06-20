package com.example.week3day13project.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PasswordHandlerTest {

    private String userPassword;
    private final int SALT_LENGTH = 20;

    @Test
    public void sameStringResultsSameSaltedValueTest() {
        String storedSalt = "testSalt";
        String hashedUserPassword = PasswordHandler.hashPassword("testPassword", storedSalt);

        String userInput = "testPassword";
        boolean result = PasswordHandler.isValidUserPassword(userInput, hashedUserPassword, storedSalt);
        assertThat(result).isTrue();
    }

    @Test
    public void getSaltValueTest() {
        String generatedSaltValue = PasswordHandler.getSalt(SALT_LENGTH);
        assertThat(generatedSaltValue).isNotNull();
        assertThat(generatedSaltValue).isNotEqualTo(PasswordHandler.getSalt(SALT_LENGTH));
    }

    @Test
    public void getHashValueTest() {
        String testSaltValue = "C1ZcAb8+ioesnl8gqgSGTYL2JNQ=";
        String userInputValue = "test";
        String hashedValue = PasswordHandler.hashPassword(userInputValue, testSaltValue);
        assertThat(hashedValue).isNotEqualTo(userInputValue);
        assertThat(hashedValue).isNotEqualTo(testSaltValue);
        assertThat(hashedValue).isEqualTo(PasswordHandler.hashPassword("test", testSaltValue));
    }
}
