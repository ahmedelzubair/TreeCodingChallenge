package sa.com.tree.account.statment.treecodingchallenge.repository;

import sa.com.tree.account.statment.treecodingchallenge.entity.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);

    User save(User user);
}
