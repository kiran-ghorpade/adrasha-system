//package com.adrasha.auth;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.modelmapper.ModelMapper;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.adrasha.auth.dto.JwtUser;
//import com.adrasha.auth.exception.UserNotFoundException;
//import com.adrasha.auth.model.User;
//import com.adrasha.auth.repository.UserRepository;
//import com.adrasha.auth.service.impl.UserServiceImpl;
//import com.adrasha.auth.util.JwtUtil;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class UserServiceTest {
//
//     @InjectMocks
//    private UserServiceImpl userService;
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @Mock
//    private JwtUtil jwtUtil;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    @Mock
//    private Authentication authentication;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//
//    // ======= deleteCurrentUser() test =======
//
//    @Test
//    public void deleteCurrentUser_success() {
//        JwtUser jwtUser = new JwtUser();
//        jwtUser.setUsername("testUser");
//
//        User user = new User();
//        user.setUsername("testUser");
//
//        when(authentication.getPrincipal()).thenReturn(jwtUser);
//        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
//
//        userService.deleteCurrentUser(authentication);
//
//        verify(userRepository).delete(user);
//    }
//
//    // ======= deleteUser() tests =======
//
//    @Test
//    public void deleteUser_existingUser_success() {
//        UUID id = UUID.randomUUID();
//
//        User user = new User();
//        user.setId(id);
//
//        when(userRepository.findById(id)).thenReturn(Optional.of(user));
//
//        userService.deleteUser(id);
//
//        verify(userRepository).delete(user);
//    }
//
//    @Test
//    public void deleteUser_userNotFound_throwsException() {
//        UUID id = UUID.randomUUID();
//
//        when(userRepository.findById(id)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(id));
//    }
//
//    // ======= getCurrentUser() tests =======
//
//    @Test
//    public void getCurrentUser_success() {
//        JwtUser jwtUser = new JwtUser();
//        jwtUser.setUsername("testUser");
//
//        User user = new User();
//        user.setUsername("testUser");
//
//        when(authentication.getPrincipal()).thenReturn(jwtUser);
//        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
//
//        User result = userService.getCurrentUser(authentication);
//
//        assertEquals(user, result);
//    }
//
//    @Test
//    public void getCurrentUser_userNotAuthenticated_throwsException() {
//        JwtUser jwtUser = new JwtUser();
//        jwtUser.setUsername("testUser");
//
//        when(authentication.getPrincipal()).thenReturn(jwtUser);
//        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());
//
//        assertThrows(BadCredentialsException.class, () -> userService.getCurrentUser(authentication));
//    }
//}
//
