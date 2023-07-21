package com.mintmoose.springbootebay.api.controller;

import com.mintmoose.springbootebay.Config.AuthenticationResponse;
import com.mintmoose.springbootebay.Config.RegisterRequest;
import com.mintmoose.springbootebay.Controller.Authentication.RegistrationController;
import com.mintmoose.springbootebay.Service.RegistrationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTests {

    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private RegistrationController registrationController;

    @Test
    public void testRegister_Success() {
        // Mock the RegistrationService behavior
        RegisterRequest registerRequest = new RegisterRequest("username", "John Doe", "email", "password");
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("token123");
        when(registrationService.register(registerRequest)).thenReturn(authenticationResponse);

        // Perform the test
        ResponseEntity<AuthenticationResponse> responseEntity = registrationController.register(registerRequest);

        // Assert the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(authenticationResponse, responseEntity.getBody());
    }

}
