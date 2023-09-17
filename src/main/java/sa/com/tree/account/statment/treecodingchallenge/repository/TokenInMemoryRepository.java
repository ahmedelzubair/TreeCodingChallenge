package sa.com.tree.account.statment.treecodingchallenge.repository;

import org.springframework.stereotype.Repository;
import sa.com.tree.account.statment.treecodingchallenge.entity.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class TokenInMemoryRepository implements TokenRepository {

    private static final HashMap<String, Token> tokens = new HashMap<>();

    @Override
    public List<Token> findAllValidTokenByUser(String username) {
        return tokens.values().stream().filter(t -> t.getUser().getUsername().equals(username)).toList();
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return tokens.values().stream().filter(t -> t.getToken().equals(token)).findFirst();
    }

    @Override
    public void saveAll(List<Token> validUserTokens) {
        validUserTokens.forEach(token -> tokens.put(token.getUser().getUsername(), token));
    }

    @Override
    public void save(Token token) {
        tokens.put(token.getUser().getUsername(), token);
    }
}
