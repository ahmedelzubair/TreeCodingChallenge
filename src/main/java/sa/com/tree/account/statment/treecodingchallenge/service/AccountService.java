package sa.com.tree.account.statment.treecodingchallenge.service;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sa.com.tree.account.statment.treecodingchallenge.entity.Account;
import sa.com.tree.account.statment.treecodingchallenge.mapper.AccountMapper;

@Service
@AllArgsConstructor
public class AccountService {

    private final JdbcTemplate jdbcTemplate;

    public Account findAccountByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM Account WHERE accountNumber = ?";
        return jdbcTemplate.queryForObject(sql, new AccountMapper(), accountNumber);
    }

    // Implement hashing logic
}