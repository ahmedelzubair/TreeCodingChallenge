package sa.com.tree.account.statment.treecodingchallenge.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sa.com.tree.account.statment.treecodingchallenge.entity.Statement;
import sa.com.tree.account.statment.treecodingchallenge.mapper.StatementMapper;

import java.util.List;

@Repository
@AllArgsConstructor
public class StatementJDBCRepository implements StatementRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Statement> findAllStatementsByAccountId(Long accountId) {
        String sql = "SELECT * FROM Statement WHERE account_id = ?";
        return jdbcTemplate.query(sql, new StatementMapper(), accountId);
    }


}
