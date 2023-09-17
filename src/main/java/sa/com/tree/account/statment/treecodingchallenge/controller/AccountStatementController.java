package sa.com.tree.account.statment.treecodingchallenge.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sa.com.tree.account.statment.treecodingchallenge.service.AccountStatementService;
import sa.com.tree.account.statment.treecodingchallenge.service.AuthorizationChecker;
import sa.com.tree.account.statment.treecodingchallenge.utils.ApiResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountStatementController {

    private final AccountStatementService accountStatementService;
    private final AuthorizationChecker authorizationChecker;

    @GetMapping("/{accountId}/statements")
    public ResponseEntity<ApiResponse> getStatementsForAccount(
            @PathVariable("accountId") @NonNull Long accountId,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @RequestParam(value = "fromAmount", required = false) String fromAmount,
            @RequestParam(value = "toAmount", required = false) String toAmount,
            Authentication authentication) {

        if (authorizationChecker.isAdmin(authentication)) {
            return accountStatementService.handleAdminRequest(accountId, fromDate, toDate, fromAmount, toAmount);
        } else if (authorizationChecker.isUser(authentication)) {
            if (fromDate != null || toDate != null || fromAmount != null || toAmount != null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } else {
                return accountStatementService.handleUserRequest(accountId);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
