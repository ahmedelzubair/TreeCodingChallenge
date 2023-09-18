package sa.com.tree.account.statment.treecodingchallenge.service;


import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sa.com.tree.account.statment.treecodingchallenge.dto.LoginDTO;
import sa.com.tree.account.statment.treecodingchallenge.dto.LoginResponseDTO;
import sa.com.tree.account.statment.treecodingchallenge.dto.TokenType;
import sa.com.tree.account.statment.treecodingchallenge.entity.Token;
import sa.com.tree.account.statment.treecodingchallenge.entity.User;
import sa.com.tree.account.statment.treecodingchallenge.exception.LoginException;
import sa.com.tree.account.statment.treecodingchallenge.exception.UserAlreadyLoggedInException;
import sa.com.tree.account.statment.treecodingchallenge.repository.TokenRepository;
import sa.com.tree.account.statment.treecodingchallenge.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private SessionManagementService sessionManagementService;

    @InjectMocks
    private UserService userService;

    @Test
    void givenValidLoginData_whenLogin_thenGenerateValidTokenAndSaveTokenAndAddSession() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // Mock JWT token generation
        when(jwtService.generateToken(user)).thenReturn("valid-token");

        // Mock session management
        HttpSession session = mock(HttpSession.class);
        when(sessionManagementService.isUserLoggedIn("testuser")).thenReturn(false);

        LoginDTO loginDTO = new LoginDTO("testuser", "password");

        // When
        LoginResponseDTO response = userService.login(loginDTO, session);

        // Then
        assertNotNull(response);
        assertEquals("valid-token", response.getAccessToken());
        verify(tokenRepository, times(1)).save(any());
        verify(sessionManagementService, times(1)).addSession("testuser", session);
    }

    @Test
    void givenInvalidUsernameOrPassword_whenLogin_thenThrowLoginException() {
        // Given
        doThrow(new LoginException("Invalid credentials")).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        LoginDTO loginDTO = new LoginDTO("invaliduser", "wrongpassword");

        // When & Then
        assertThrows(LoginException.class, () -> userService.login(loginDTO, mock(HttpSession.class)));
    }

    @Test
    void givenUserNotFound_whenLogin_thenThrowUsernameNotFoundException() {
        // Given
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        LoginDTO loginDTO = new LoginDTO("nonexistentuser", "password");

        // When & Then
        assertThrows(UsernameNotFoundException.class, () -> userService.login(loginDTO, mock(HttpSession.class)));
    }

    @Test
    void givenValidUserTokensExist_whenLogin_thenRevokeTokensAndSaveRevokedTokens() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        List<Token> validUserTokens = List.of(
                new Token(1, "token1", TokenType.BEARER, false, false, user),
                new Token(2, "token2", TokenType.BEARER, false, false, user)
        );
        when(tokenRepository.findAllValidTokenByUser("testuser")).thenReturn(validUserTokens);

        // When
        userService.login(new LoginDTO("testuser", "password"), mock(HttpSession.class));

        // Then
        verify(tokenRepository, times(1)).saveAll(validUserTokens);
        validUserTokens.forEach(token -> {
            assertTrue(token.isExpired());
            assertTrue(token.isRevoked());
        });
    }

    @Test
    void givenValidUser_whenLogin_thenSaveUserToken() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        when(jwtService.generateToken(user)).thenReturn("valid-token");

        LoginDTO loginDTO = new LoginDTO("testuser", "password");

        // When
        userService.login(loginDTO, mock(HttpSession.class));

        // Then
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void givenValidUser_whenLogin_thenAddUserSession() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        when(jwtService.generateToken(user)).thenReturn("valid-token");

        HttpSession session = mock(HttpSession.class);
        when(sessionManagementService.isUserLoggedIn("testuser")).thenReturn(false);

        LoginDTO loginDTO = new LoginDTO("testuser", "password");

        // When
        userService.login(loginDTO, session);

        // Then
        verify(sessionManagementService, times(1)).addSession("testuser", session);
    }

    @Test
    void givenUserAlreadyLoggedIn_whenLogin_thenThrowUserAlreadyLoggedInException() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        HttpSession session = mock(HttpSession.class);
        when(sessionManagementService.isUserLoggedIn("testuser")).thenReturn(true);

        LoginDTO loginDTO = new LoginDTO("testuser", "password");

        // When & Then
        assertThrows(UserAlreadyLoggedInException.class, () -> userService.login(loginDTO, session));
    }

    @Test
    void givenLoadTestingScenario_whenLoginMultipleTimes_thenAllLoginsAreSuccessful() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        when(jwtService.generateToken(user)).thenReturn("valid-token");

        HttpSession session = mock(HttpSession.class);
        when(sessionManagementService.isUserLoggedIn("testuser")).thenReturn(false);

        int numConcurrentLogins = 1000;

        // When & Then
        for (int i = 0; i < numConcurrentLogins; i++) {
            LoginDTO loginDTO = new LoginDTO("testuser", "password");
            LoginResponseDTO response = userService.login(loginDTO, session);
            assertNotNull(response);
            assertEquals("valid-token", response.getAccessToken());
        }

        // Verify
        verify(tokenRepository, times(numConcurrentLogins)).save(any(Token.class));
        verify(sessionManagementService, times(numConcurrentLogins)).addSession("testuser", session);
    }

    @Test
    void givenInvalidInput_whenLoginWithNullOrEmptyUsernameOrPassword_thenThrowUsernameNotFoundException() {
        // When & Then
        assertThrows(UsernameNotFoundException.class, () -> userService.login(new LoginDTO(null, "password"), mock(HttpSession.class)));
        assertThrows(UsernameNotFoundException.class, () -> userService.login(new LoginDTO("", "password"), mock(HttpSession.class)));
        assertThrows(UsernameNotFoundException.class, () -> userService.login(new LoginDTO("username", null), mock(HttpSession.class)));
        assertThrows(UsernameNotFoundException.class, () -> userService.login(new LoginDTO("username", ""), mock(HttpSession.class)));
    }

    @Test
    void givenUnexpectedErrorDuringAuthentication_whenLogin_thenThrowLoginException() {
        // Given
        doThrow(new RuntimeException("Database error")).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        LoginDTO loginDTO = new LoginDTO("testuser", "password");

        // When & Then
        assertThrows(LoginException.class, () -> userService.login(loginDTO, mock(HttpSession.class)));
    }

}
