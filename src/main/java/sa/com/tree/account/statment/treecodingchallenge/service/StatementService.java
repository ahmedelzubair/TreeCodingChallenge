package sa.com.tree.account.statment.treecodingchallenge.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sa.com.tree.account.statment.treecodingchallenge.dto.SearchCriteriaDTO;
import sa.com.tree.account.statment.treecodingchallenge.dto.StatementDTO;
import sa.com.tree.account.statment.treecodingchallenge.entity.Statement;
import sa.com.tree.account.statment.treecodingchallenge.mapper.MappingHelper;
import sa.com.tree.account.statment.treecodingchallenge.mapper.StatementMapper;
import sa.com.tree.account.statment.treecodingchallenge.utils.SearchUtils;
import sa.com.tree.account.statment.treecodingchallenge.validators.SearchCriteriaValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class StatementService {

    private final JdbcTemplate jdbcTemplate;
    private final SearchCriteriaValidator searchCriteriaValidator;

    public List<StatementDTO> getStatementsByCriteria(SearchCriteriaDTO searchCriteriaDTO) {
        searchCriteriaValidator.validate(searchCriteriaDTO);

        List<Statement> allStatements = fetchAllStatements(searchCriteriaDTO.getAccountId());
        List<Statement> filteredStatements = filterStatements(allStatements, searchCriteriaDTO);

        return MappingHelper.hashAccountIdAndMapQueryResult(filteredStatements);
    }

    private List<Statement> fetchAllStatements(Long accountId) {
        String sql = "SELECT * FROM Statement WHERE account_id = ?";
        return jdbcTemplate.query(sql, new StatementMapper(), accountId);
    }

    private List<Statement> filterStatements(List<Statement> statements, SearchCriteriaDTO searchCriteriaDTO) {
        LocalDate fromDate = SearchUtils.parseDate(searchCriteriaDTO.getFromDate());
        LocalDate toDate = SearchUtils.parseDate(searchCriteriaDTO.getToDate());
        BigDecimal fromAmount = SearchUtils.parseAmount(searchCriteriaDTO.getFromAmount());
        BigDecimal toAmount = SearchUtils.parseAmount(searchCriteriaDTO.getToAmount());

        return statements.stream()
                .filter(statement -> isStatementInDateRange(statement, fromDate, toDate) &&
                        isStatementInAmountRange(statement, fromAmount, toAmount))
                .collect(Collectors.toList());
    }

    private boolean isStatementInDateRange(Statement statement, LocalDate fromDate, LocalDate toDate) {
        LocalDate statementDate = LocalDate.parse(statement.getDateField(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return statementDate.isAfter(fromDate.minusDays(1)) && statementDate.isBefore(toDate.plusDays(1));
    }

    private boolean isStatementInAmountRange(Statement statement, BigDecimal fromAmount, BigDecimal toAmount) {
        BigDecimal statementAmount = SearchUtils.parseAmount(statement.getAmount());
        return statementAmount.compareTo(fromAmount) >= 0 && statementAmount.compareTo(toAmount) <= 0;
    }

}