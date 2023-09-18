package sa.com.tree.account.statment.treecodingchallenge.mapper;

import sa.com.tree.account.statment.treecodingchallenge.dto.StatementDTO;
import sa.com.tree.account.statment.treecodingchallenge.entity.Statement;
import sa.com.tree.account.statment.treecodingchallenge.utils.HashingUtils;

import java.util.HashSet;
import java.util.Set;

public class MappingHelper {


    public static Set<StatementDTO> hashAccountIdAndMapQueryResult(Set<Statement> statements) {
        // Map and hash the account id before returning the result as StatementDTO
        Set<StatementDTO> statementDTOs = new HashSet<>();
        for (Statement statement : statements) {
            StatementDTO statementDTO = new StatementDTO();
            statementDTO.setId(statement.getId());
            statementDTO.setAccountId(HashingUtils.hash(statement.getAccountId()));
            statementDTO.setStatementDate(statement.getDateField());
            statementDTO.setAmount(statement.getAmount());
            statementDTOs.add(statementDTO);
        }
        return statementDTOs;
    }

}
