package sa.com.tree.account.statment.treecodingchallenge.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidJWTException extends AuthenticationException {

    public InvalidJWTException(String message) {
        super(message);
    }

}
