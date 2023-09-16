package sa.com.tree.account.statment.treecodingchallenge.controller;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sa.com.tree.account.statment.treecodingchallenge.dto.SearchCriteriaDTO;
import sa.com.tree.account.statment.treecodingchallenge.dto.StatementDTO;
import sa.com.tree.account.statment.treecodingchallenge.service.StatementService;
import sa.com.tree.account.statment.treecodingchallenge.utils.ApiResponse;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountStatementController {


    private final StatementService statementService;

    @GetMapping("/{accountId}/statements")
    public ResponseEntity<ApiResponse> getStatementsForAccount(
            @PathVariable("accountId") @NonNull Long accountId,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @RequestParam(value = "fromAmount", required = false) String fromAmount,
            @RequestParam(value = "toAmount", required = false) String toAmount) {
        SearchCriteriaDTO searchCriteriaDTO = SearchCriteriaDTO.builder()
                .accountId(accountId)
                .fromDate(fromDate)
                .toDate(toDate)
                .fromAmount(fromAmount)
                .toAmount(toAmount)
                .build();
        Set<StatementDTO> statements = statementService.getStatementsByCriteria(searchCriteriaDTO);
        ApiResponse apiResponse = ApiResponse.builder().status(200)
                .timestamp(LocalDateTime.now())
                .message(!statements.isEmpty() ? "Successfully retrieved account statements" : "No statements found")
                .data(statements)
                .build();
        return ResponseEntity.ok(apiResponse);
    }


}
