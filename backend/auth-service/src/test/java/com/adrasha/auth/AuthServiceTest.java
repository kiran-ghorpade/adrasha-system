package com.adrasha.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.adrasha.auth.dto.AuthTokenResponse;
import com.adrasha.auth.dto.LoginRequest;
import com.adrasha.auth.dto.RegistrationRequest;
import com.adrasha.auth.dto.UserDTO;
import com.adrasha.auth.dto.core.JwtUser;
import com.adrasha.auth.exception.UserAlreadyExistsException;
import com.adrasha.auth.exception.UserNotFoundException;
import com.adrasha.auth.model.User;
import com.adrasha.auth.repository.UserRepository;
import com.adrasha.auth.service.impl.AuthServiceImpl;
import com.adrasha.auth.util.JwtUtil;

@SpringBootTest
@AutoConfigureMockMvc
class AuthServiceTest {

     @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // ======= register() tests =======

    @Test
    public void register_existingUser_throwsUserAlreadyExistsException() {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("existingUser");
        request.setPassword("pass");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> authService.register(request));

        verify(userRepository, never()).save(any());
    }

    @Test
    public void register_newUser_success() {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("newUser");
        request.setPassword("pass");

        User userEntity = new User();
        userEntity.setUsername("newUser");
        userEntity.setPassword("hashedPass");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("newUser");

        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass")).thenReturn("hashedPass");
        when(modelMapper.map(request, User.class)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserDTO.class)).thenReturn(userDTO);

        UserDTO result = authService.register(request);

        assertNotNull(result);
        assertEquals("newUser", result.getUsername());

        verify(userRepository).save(userEntity);
    }

    // ======= login() tests =======

    @Test
    public void login_success() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUser");
        loginRequest.setPassword("pass");

        Authentication auth = mock(Authentication.class);
        User user = new User();
        user.setUsername("testUser");

        JwtUser jwtUser = new JwtUser();
        jwtUser.setUsername("testUser");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(auth.getName()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(modelMapper.map(user, JwtUser.class)).thenReturn(jwtUser);
        when(jwtUtil.generateToken(jwtUser)).thenReturn("token");
        when(jwtUtil.getExpiration()).thenReturn(1000L);

        AuthTokenResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("token", response.getAccessToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals(1000L, response.getExpiresIn());
        assertTrue(response.getExp().isAfter(Instant.now()));
    }

    @Test
    public void login_userNotFound_throwsException() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUser");
        loginRequest.setPassword("pass");

        Authentication auth = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(auth.getName()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authService.login(loginRequest));
    }

    // ======= resetPassword() tests =======

//    @Test
//    public void resetPassword_success() {
//        PasswordResetRequest req = new PasswordResetRequest();
//        req.setOldPassword("oldPass");
//        req.setNewPassword("newPass");
//
//        User user = new User();
//        user.setPassword("hashedOldPass");
//
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername("testUser");
//
//        JwtUser jwtUser = new JwtUser();
//        jwtUser.setUsername("testUser");
//
//        when(authentication.getPrincipal()).thenReturn(jwtUser);
//        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
//        when(passwordEncoder.matches("oldPass", "hashedOldPass")).thenReturn(true);
//        when(passwordEncoder.matches("newPass", "hashedOldPass")).thenReturn(false);
//        when(passwordEncoder.encode("newPass")).thenReturn("hashedNewPass");
//        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
//
//        UserDTO result = authService.resetPassword(authentication, req);
//
//        assertNotNull(result);
//        verify(userRepository).save(user);
//        assertEquals("hashedNewPass", user.getPassword());
//    }
//
//    @Test
//    public void resetPassword_oldPasswordMismatch_throwsBadCredentials() {
//        PasswordResetRequest req = new PasswordResetRequest();
//        req.setOldPassword("wrongOld");
//        req.setNewPassword("newPass");
//
//        User user = new User();
//        user.setPassword("hashedOldPass");
//
//        JwtUser jwtUser = new JwtUser();
//        jwtUser.setUsername("testUser");
//
//        when(authentication.getPrincipal()).thenReturn(jwtUser);
//        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
//        when(passwordEncoder.matches("wrongOld", "hashedOldPass")).thenReturn(false);
//
//        assertThrows(BadCredentialsException.class, () -> authService.resetPassword(authentication, req));
//    }
//
//    @Test
//    public void resetPassword_newPasswordSameAsOld_throwsIllegalArgument() {
//        PasswordResetRequest req = new PasswordResetRequest();
//        req.setOldPassword("oldPass");
//        req.setNewPassword("oldPass");
//
//        User user = new User();
//        user.setPassword("hashedOldPass");
//
//        JwtUser jwtUser = new JwtUser();
//        jwtUser.setUsername("testUser");
//
//        when(authentication.getPrincipal()).thenReturn(jwtUser);
//        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
//        when(passwordEncoder.matches("oldPass", "hashedOldPass")).thenReturn(true);
//        when(passwordEncoder.matches("oldPass", "hashedOldPass")).thenReturn(true); // new password same as old
//
//        assertThrows(IllegalArgumentException.class, () -> authService.resetPassword(authentication, req));
//    }
  
}

