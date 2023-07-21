package com.mintmoose.springbootebay.api.controller;

import com.mintmoose.springbootebay.Config.AuthenticationRequest;
import com.mintmoose.springbootebay.Config.AuthenticationResponse;
import com.mintmoose.springbootebay.Controller.Authentication.AuthenticationController;
import com.mintmoose.springbootebay.Service.AuthenticationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTests {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private MockHttpServletResponse mockResponse;

    @Before
    public void setUp() {
        mockResponse = new MockHttpServletResponse();
    }

    @Test
    public void testAuthenticate_Success() {
        // Mock the AuthenticationService behavior
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("username", "password");
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("token123");
        when(authenticationService.authenticate(authenticationRequest, mockResponse)).thenReturn(authenticationResponse);

        // Perform the test
        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.authenticate(authenticationRequest, mockResponse);

        // Assert the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(authenticationResponse, responseEntity.getBody());
    }
}
