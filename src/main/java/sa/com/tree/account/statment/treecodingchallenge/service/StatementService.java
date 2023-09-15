package sa.com.tree.account.statment.treecodingchallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sa.com.tree.account.statment.treecodingchallenge.entity.Statement;
import sa.com.tree.account.statment.treecodingchallenge.mapper.StatementMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatementService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Statement> getStatementsByCriteria(Long accountId, LocalDate fromDate, LocalDate toDate, BigDecimal fromAmount, BigDecimal toAmount) {
        String sql = "SELECT * FROM Statement WHERE accountId = ?";
        List<Object> params = new ArrayList<>();
        params.add(accountId);

        if (fromDate != null) {
            sql += " AND datefield >= ?";
            params.add(fromDate.toString());
        }

        if (toDate != null) {
            sql += " AND datefield <= ?";
            params.add(toDate.toString());
        }

        if (fromAmount != null) {
            sql += " AND amount >= ?";
            params.add(fromAmount.toString());
        }

        if (toAmount != null) {
            sql += " AND amount <= ?";
            params.add(toAmount.toString());
        }

        return jdbcTemplate.query(sql, params.toArray(), new StatementMapper());
    }
}