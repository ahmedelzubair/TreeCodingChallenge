package sa.com.tree.account.statment.treecodingchallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sa.com.tree.account.statment.treecodingchallenge.dto.SearchCriteriaDTO;
import sa.com.tree.account.statment.treecodingchallenge.dto.StatementDTO;
import sa.com.tree.account.statment.treecodingchallenge.utils.ApiResponse;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class AccountStatementService {

    private final StatementService statementService;

    @Autowired
    public AccountStatementService(StatementService statementService) {
        this.statementService = statementService;
    }

    public ResponseEntity<ApiResponse> handleAdminRequest(
            Long accountId, String fromDate, String toDate, String fromAmount, String toAmount) {
        SearchCriteriaDTO searchCriteriaDTO = SearchCriteriaDTO.builder()
                .accountId(accountId)
                .fromDate(fromDate)
                .toDate(toDate)
                .fromAmount(fromAmount)
                .toAmount(toAmount)
                .build();
        Set<StatementDTO> statements = statementService.getStatementsByCriteria(searchCriteriaDTO);
        return createResponse(statements);
    }

    public ResponseEntity<ApiResponse> handleUserRequest(Long accountId) {
        // Implement logic to get the three months back statement for the user.
        Set<StatementDTO> statements = statementService.getThreeMonthsBackStatement(accountId);
        return createResponse(statements);
    }

    private ResponseEntity<ApiResponse> createResponse(Set<StatementDTO> statements) {
        ApiResponse apiResponse = ApiResponse.builder()
                .status(200)
                .timestamp(LocalDateTime.now())
                .message(!statements.isEmpty() ? "Successfully retrieved account statements" : "No statements found")
                .data(statements)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
