package sa.com.tree.account.statment.treecodingchallenge.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AccountJDBCRepository {

    private final JdbcTemplate jdbcTemplate;


}
