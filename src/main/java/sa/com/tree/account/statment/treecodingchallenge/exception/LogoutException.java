package sa.com.tree.account.statment.treecodingchallenge.exception;

import org.springframework.security.core.AuthenticationException;

public class LogoutException extends AuthenticationException {

    public LogoutException(String message) {
        super(message);
    }

}
