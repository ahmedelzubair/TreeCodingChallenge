package sa.com.tree.account.statment.treecodingchallenge.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import sa.com.tree.account.statment.treecodingchallenge.dto.SearchCriteriaDTO;
import sa.com.tree.account.statment.treecodingchallenge.dto.StatementDTO;
import sa.com.tree.account.statment.treecodingchallenge.entity.Statement;
import sa.com.tree.account.statment.treecodingchallenge.exception.StatementSearchException;
import sa.com.tree.account.statment.treecodingchallenge.mapper.MappingHelper;
import sa.com.tree.account.statment.treecodingchallenge.repository.StatementRepository;
import sa.com.tree.account.statment.treecodingchallenge.utils.DateUtils;
import sa.com.tree.account.statment.treecodingchallenge.utils.SearchUtils;
import sa.com.tree.account.statment.treecodingchallenge.validator.SearchCriteriaValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class StatementService {

    private final StatementRepository statementRepository;
    private final SearchCriteriaValidator searchCriteriaValidator;

    public Set<StatementDTO> getStatementsByCriteria(SearchCriteriaDTO searchCriteriaDTO) {
        searchCriteriaValidator.validate(searchCriteriaDTO);
        log.info("[StatementService] Getting statements by criteria: {}", searchCriteriaDTO);
        try {
            List<Statement> allStatements = statementRepository.findAllStatementsByAccountId(searchCriteriaDTO.getAccountId());

            Set<Statement> filteredStatements;
            if (hasValidDateRange(searchCriteriaDTO) && hasValidAmountRange(searchCriteriaDTO)) {
                filteredStatements = filterStatementsByDateAndAmount(allStatements, searchCriteriaDTO);
            } else if (hasValidDateRange(searchCriteriaDTO)) {
                filteredStatements = filterStatementsByDate(allStatements, searchCriteriaDTO);
            } else if (hasValidAmountRange(searchCriteriaDTO)) {
                filteredStatements = filterStatementsByAmount(allStatements, searchCriteriaDTO);
            } else {
                filteredStatements = getDefaultStatements(allStatements);
            }
            return MappingHelper.hashAccountIdAndMapQueryResult(filteredStatements);
        } catch (Exception e) {
            log.error("[StatementService] Error while getting statements by criteria: {} , error: {}", searchCriteriaDTO, e.getMessage());
            throw new StatementSearchException(e);
        }
    }

    private Set<Statement> filterStatementsByDateAndAmount(List<Statement> statements, SearchCriteriaDTO searchCriteriaDTO) {
        LocalDate fromDate = SearchUtils.parseDate(searchCriteriaDTO.getFromDate());
        LocalDate toDate = SearchUtils.parseDate(searchCriteriaDTO.getToDate());
        BigDecimal fromAmount = SearchUtils.parseAmount(searchCriteriaDTO.getFromAmount());
        BigDecimal toAmount = SearchUtils.parseAmount(searchCriteriaDTO.getToAmount());

        return statements.stream()
                .filter(statement -> isStatementInDateRange(statement, fromDate, toDate))
                .filter(statement -> isStatementInAmountRange(statement, fromAmount, toAmount))
                .collect(Collectors.toSet());
    }

    private Set<Statement> filterStatementsByDate(List<Statement> statements, SearchCriteriaDTO searchCriteriaDTO) {
        LocalDate fromDate = SearchUtils.parseDate(searchCriteriaDTO.getFromDate());
        LocalDate toDate = SearchUtils.parseDate(searchCriteriaDTO.getToDate());

        return statements.stream()
                .filter(statement -> isStatementInDateRange(statement, fromDate, toDate))
                .collect(Collectors.toSet());
    }

    private Set<Statement> filterStatementsByAmount(List<Statement> statements, SearchCriteriaDTO searchCriteriaDTO) {
        BigDecimal fromAmount = SearchUtils.parseAmount(searchCriteriaDTO.getFromAmount());
        BigDecimal toAmount = SearchUtils.parseAmount(searchCriteriaDTO.getToAmount());

        return statements.stream()
                .filter(statement -> isStatementInAmountRange(statement, fromAmount, toAmount))
                .collect(Collectors.toSet());
    }

    private Set<Statement> getDefaultStatements(List<Statement> statements) {
        LocalDate fromDate = LocalDate.now().minusMonths(3);
        LocalDate toDate = LocalDate.now();

        return statements.stream()
                .filter(statement -> isStatementInDateRange(statement, fromDate, toDate))
                .collect(Collectors.toSet());
    }

    private boolean hasValidDateRange(SearchCriteriaDTO searchCriteriaDTO) {
        return StringUtils.isNotEmpty(searchCriteriaDTO.getFromDate()) &&
                StringUtils.isNotEmpty(searchCriteriaDTO.getToDate());
    }

    private boolean hasValidAmountRange(SearchCriteriaDTO searchCriteriaDTO) {
        return StringUtils.isNotEmpty(searchCriteriaDTO.getFromAmount()) &&
                StringUtils.isNotEmpty(searchCriteriaDTO.getToAmount());
    }

    private boolean isStatementInDateRange(Statement statement, LocalDate fromDate, LocalDate toDate) {
        LocalDate statementDate = LocalDate.parse(statement.getDateField(), DateTimeFormatter.ofPattern(DateUtils.DATE_FORMAT));
        return !statementDate.isBefore(fromDate) && !statementDate.isAfter(toDate);
    }

    private boolean isStatementInAmountRange(Statement statement, BigDecimal fromAmount, BigDecimal toAmount) {
        BigDecimal statementAmount = SearchUtils.parseAmount(statement.getAmount());
        return statementAmount.compareTo(fromAmount) >= 0 && statementAmount.compareTo(toAmount) <= 0;
    }

    public Set<StatementDTO> getThreeMonthsBackStatement(Long accountId) {
        List<Statement> allStatements = statementRepository.findAllStatementsByAccountId(accountId);
        Set<Statement> filteredStatements = getDefaultStatements(allStatements);
        log.info("filteredStatements: {}", filteredStatements);
        return MappingHelper.hashAccountIdAndMapQueryResult(filteredStatements);
    }
}
