package sa.com.tree.account.statment.treecodingchallenge.repository;

import org.springframework.stereotype.Repository;
import sa.com.tree.account.statment.treecodingchallenge.dto.User;

import java.util.HashMap;
import java.util.Optional;

@Repository
public class UserInMemoryRepository implements UserRepository {

    // simulate in memory database
    private static final HashMap<String, User> users = new HashMap<>();

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    @Override
    public User save(User user) {
        users.put(user.getUsername(), user);
        return users.get(user.getUsername());
    }

}
