package sa.com.tree.account.statment.treecodingchallenge.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sa.com.tree.account.statment.treecodingchallenge.dto.SearchCriteriaDTO;
import sa.com.tree.account.statment.treecodingchallenge.dto.StatementDTO;
import sa.com.tree.account.statment.treecodingchallenge.service.StatementService;
import sa.com.tree.account.statment.treecodingchallenge.utils.ApiResponse;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/statements")
public class StatementController {


    private final StatementService statementService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getStatementsForAccount(
            @RequestParam("accountId") Long accountId,
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
        List<StatementDTO> statements = statementService.getStatementsByCriteria(searchCriteriaDTO);
        ApiResponse apiResponse = ApiResponse.builder().status(200).message("Success").data(statements).build();
        return ResponseEntity.ok(apiResponse);
    }


}
