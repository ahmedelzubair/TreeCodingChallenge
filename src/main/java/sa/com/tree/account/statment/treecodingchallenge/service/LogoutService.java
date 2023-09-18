package sa.com.tree.account.statment.treecodingchallenge.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import sa.com.tree.account.statment.treecodingchallenge.entity.Token;
import sa.com.tree.account.statment.treecodingchallenge.exception.InvalidJWTException;
import sa.com.tree.account.statment.treecodingchallenge.exception.LogoutException;
import sa.com.tree.account.statment.treecodingchallenge.repository.TokenRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final SessionManagementService sessionManagementService;
    private final JwtService jwtService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return;
            }
            jwt = authHeader.substring(7);
            revokeUserJWTAndSession(jwt);
        } catch (Exception e) {
            log.error("Error while logging out : ", e);
            throw new LogoutException("Error while logging out");
        }

    }

    private void revokeUserJWTAndSession(String jwt) {
        String username = jwtService.extractUsername(jwt);
        Token storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            sessionManagementService.removeSession(username);
            SecurityContextHolder.clearContext();
        } else {
            throw new InvalidJWTException("Invalid JWT token");
        }
    }


}
