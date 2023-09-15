package sa.com.tree.account.statment.treecodingchallenge.mapper;

import org.springframework.jdbc.core.RowMapper;
import sa.com.tree.account.statment.treecodingchallenge.entity.Account;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getLong("id"));
        account.setAccountType(resultSet.getString("account_type"));
        account.setAccountNumber(resultSet.getString("account_number"));
        return account;
    }
}
