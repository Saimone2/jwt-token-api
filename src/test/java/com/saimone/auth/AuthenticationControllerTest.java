package com.saimone.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private AuthenticationService authenticationService;

    private AuthenticationController authenticationController;

    @BeforeEach
    public void setup() {
        authenticationController = new AuthenticationController(authenticationService);
    }

    @Test
    void registerTest() {
        //given
        RegisterRequest mockRequest = new RegisterRequest();
        AuthenticationResponse mockResponse = new AuthenticationResponse();

        //when
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(mockResponse);

        ResponseEntity<AuthenticationResponse> response = authenticationController.register(mockRequest);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void authenticateTest() {
        //given
        AuthenticationRequest mockRequest = new AuthenticationRequest();
        AuthenticationResponse mockResponse = new AuthenticationResponse();

        //when
        when(authenticationService.authenticate(any(AuthenticationRequest.class))).thenReturn(mockResponse);

        ResponseEntity<AuthenticationResponse> response = authenticationController.authenticate(mockRequest);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void refreshTokenTest() throws IOException {
        //when
        authenticationController.refreshToken(request, response);

        //then
        verify(authenticationService).refreshToken(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }
}