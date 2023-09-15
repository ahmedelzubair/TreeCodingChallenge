package sa.com.tree.account.statment.treecodingchallenge.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sa.com.tree.account.statment.treecodingchallenge.dto.SearchCriteriaDTO;
import sa.com.tree.account.statment.treecodingchallenge.dto.StatementDTO;
import sa.com.tree.account.statment.treecodingchallenge.entity.Statement;
import sa.com.tree.account.statment.treecodingchallenge.exception.InvalidSearchCriteriaException;
import sa.com.tree.account.statment.treecodingchallenge.mapper.StatementMapper;
import sa.com.tree.account.statment.treecodingchallenge.utils.HashingUtils;
import sa.com.tree.account.statment.treecodingchallenge.validators.SearchCriteriaValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class StatementService {

    private final JdbcTemplate jdbcTemplate;
    private final SearchCriteriaValidator searchCriteriaValidator;


    public List<StatementDTO> getStatementsByCriteria(SearchCriteriaDTO searchCriteriaDTO) {

        searchCriteriaValidator.validate(searchCriteriaDTO);

        String sql = "SELECT * FROM Statement WHERE account_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(searchCriteriaDTO.getAccountId());

        if (searchCriteriaDTO.getFromDate() == null && searchCriteriaDTO.getToDate() == null) {
            // Calculate the three months back date
            LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
            sql += " AND datefield >= ?";
            params.add(threeMonthsAgo.toString());
        } else if (searchCriteriaDTO.getFromDate() != null && searchCriteriaDTO.getToDate() != null) {
            sql += " AND datefield BETWEEN ? AND ?";
            params.add(searchCriteriaDTO.getFromDate().toString());
            params.add(searchCriteriaDTO.getToDate().toString());
        } else {
            log.error("[StatementService] Invalid search criteria, from or to date is missing");
            throw new InvalidSearchCriteriaException("Please provide both from and to dates");
        }

        if (searchCriteriaDTO.getFromAmount() != null && searchCriteriaDTO.getToAmount() != null) {
            sql += " AND amount BETWEEN ? AND ?";
            params.add(searchCriteriaDTO.getFromAmount().toString());
            params.add(searchCriteriaDTO.getToAmount().toString());
        } else {
            log.error("[StatementService] Invalid search criteria, from or to amount is missing");
            throw new InvalidSearchCriteriaException("Please provide both from and to amounts");
        }

        List<Statement> statements = jdbcTemplate.query(sql, new StatementMapper(), params.toArray());
        return mapQueryResult(statements);
    }

    private List<StatementDTO> mapQueryResult(List<Statement> statements) {
        // hash the account id before returning the result and map the result to the DTO
        List<StatementDTO> statementDTOS = new ArrayList<>();
        statements.forEach(statement -> {
            StatementDTO statementDTO = new StatementDTO();
            statementDTO.setId(statement.getId());
            statementDTO.setAccountId(HashingUtils.hash(statement.getAccountId()));
            statementDTO.setDateField(statement.getDateField());
            statementDTO.setAmount(statement.getAmount());
            statementDTOS.add(statementDTO);
        });
        return statementDTOS;
    }

}