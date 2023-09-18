package sa.com.tree.account.statment.treecodingchallenge.mapper;

import org.springframework.jdbc.core.RowMapper;
import sa.com.tree.account.statment.treecodingchallenge.entity.Statement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatementMapper implements RowMapper<Statement> {

    @Override
    public Statement mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Statement statement = new Statement();
        statement.setId(resultSet.getLong("id"));
        statement.setAccountId(resultSet.getLong("account_id"));
        statement.setDateField(resultSet.getString("datefield"));
        statement.setAmount(resultSet.getString("amount"));
        return statement;
    }
}
