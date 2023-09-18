package sa.com.tree.account.statment.treecodingchallenge.service;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sa.com.tree.account.statment.treecodingchallenge.dto.LoginDTO;
import sa.com.tree.account.statment.treecodingchallenge.dto.LoginResponseDTO;
import sa.com.tree.account.statment.treecodingchallenge.dto.RegistrationDTO;
import sa.com.tree.account.statment.treecodingchallenge.dto.TokenType;
import sa.com.tree.account.statment.treecodingchallenge.entity.Token;
import sa.com.tree.account.statment.treecodingchallenge.entity.User;
import sa.com.tree.account.statment.treecodingchallenge.exception.LoginException;
import sa.com.tree.account.statment.treecodingchallenge.exception.UserAlreadyLoggedInException;
import sa.com.tree.account.statment.treecodingchallenge.repository.TokenRepository;
import sa.com.tree.account.statment.treecodingchallenge.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SessionManagementService sessionManagementService;

    public User addNewUser(RegistrationDTO request) {
        log.info("[UserService] Registering new user: {} , request: {}", request.getUsername(), request);
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        User savedUser = repository.save(user);
        log.info("[UserService] New user: {} successfully registered", user.getUsername());
        return savedUser;
    }

    public LoginResponseDTO login(LoginDTO request, HttpSession session) {
        String username = request.getUsername();
        validateUserNotLoggedIn(username);
        log.info("[UserService] Authenticating user: {}", username);
        authenticateUser(request);
        log.info("[UserService] User: {} successfully authenticated", username);
        User user = findUserByUsername(username);
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        log.info("[UserService] All user tokens revoked for user: {}", username);
        saveUserToken(user, jwtToken);
        sessionManagementService.addSession(username, session);
        log.info("[UserService] User: {} successfully logged in", username);
        return buildLoginResponse(jwtToken);
    }


    private void validateUserNotLoggedIn(String username) {
        if (sessionManagementService.isUserLoggedIn(username)) {
            throw new UserAlreadyLoggedInException("User is already logged in. Please log out before attempting to log in again.");
        }
    }

    private void authenticateUser(LoginDTO request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException ex) {
            log.error("[UserService] Authentication failed for user: {} with exception: {}", request.getUsername(), ex.getMessage());
            throw new LoginException("Incorrect username or password. Please try again.");
        }
    }

    private User findUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUsername());
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private LoginResponseDTO buildLoginResponse(String accessToken) {
        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .build();
    }
}
