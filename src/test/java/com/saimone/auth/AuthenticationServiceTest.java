package com.saimone.auth;

import org.junit.jupiter.api.BeforeEach;
import com.saimone.config.JwtService;
import com.saimone.token.TokenRepository;
import com.saimone.user.Role;
import com.saimone.user.User;
import com.saimone.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenRepository tokenRepository;

    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup() {
        authenticationService = new AuthenticationService(userRepository, passwordEncoder, jwtService,authenticationManager, tokenRepository);
    }

    @Test
    public void registerShouldReturnAuthenticationResponse() {
        //given
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("Andrey");
        request.setLastname("Khrushch");
        request.setEmail("khrushch@example.com");
        request.setPassword("password");

        User savedUser = User.builder()
                .id(1L)
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        String jwtToken = "generatedJwtToken";
        String refreshToken = "generatedRefreshToken";

        //when
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtService.generateToken(any(User.class))).thenReturn(jwtToken);
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn(refreshToken);

        AuthenticationResponse response = authenticationService.register(request);

        //then
        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtService, times(1)).generateToken(savedUser);
        verify(jwtService, times(1)).generateRefreshToken(savedUser);

        Assertions.assertEquals(jwtToken, response.getAccessToken());
        Assertions.assertEquals(refreshToken, response.getRefreshToken());
    }

    @Test
    public void authenticateShouldReturnAuthenticationResponse() {
        //given
        AuthenticationRequest request = new AuthenticationRequest("khrushch@example.com", "password");
        User user = User.builder()
                .id(1L)
                .firstname("Andrey")
                .lastname("Khrushch")
                .email("khrushch@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        //when
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        when(authenticationManager.authenticate(authentication)).thenReturn(null);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

        String jwtToken = "mockedJwtToken";
        String refreshToken = "mockedRefreshToken";
        when(jwtService.generateToken(user)).thenReturn(jwtToken);
        when(jwtService.generateRefreshToken(user)).thenReturn(refreshToken);

        AuthenticationResponse response = authenticationService.authenticate(request);

        //then
        verify(authenticationManager, times(1)).authenticate(authentication);
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(jwtService, times(1)).generateToken(user);
        verify(jwtService, times(1)).generateRefreshToken(user);

        Assertions.assertEquals(jwtToken, response.getAccessToken());
        Assertions.assertEquals(refreshToken, response.getRefreshToken());
    }
}