package sa.com.tree.account.statment.treecodingchallenge.repository;

import sa.com.tree.account.statment.treecodingchallenge.dto.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository {

    List<Token> findAllValidTokenByUser(String username);

    Optional<Token> findByToken(String token);

    void saveAll(List<Token> validUserTokens);

    void save(Token token);
}
