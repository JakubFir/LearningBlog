package com.example.LearningBlog.auth;

import com.example.LearningBlog.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void testAuthenticate() {
        //Given
        String username = "testuser";
        String password = "testpassword";
        String token = "testtoken";
        AuthenticationRequest request = new AuthenticationRequest(username, password);
        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(username, password));
        when(jwtService.generateToken(username)).thenReturn(token);

        //When
        AuthenticationResponse response = authenticationService.authenticate(request);

        //Then
        assertNotNull(response);
        assertEquals(token, response.getToken());
    }
}