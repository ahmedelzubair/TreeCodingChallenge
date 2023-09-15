package sa.com.tree.account.statment.treecodingchallenge.mapper;

import sa.com.tree.account.statment.treecodingchallenge.dto.StatementDTO;
import sa.com.tree.account.statment.treecodingchallenge.entity.Statement;
import sa.com.tree.account.statment.treecodingchallenge.utils.HashingUtils;

import java.util.ArrayList;
import java.util.List;

public class MappingHelper {


    public static List<StatementDTO> hashAccountIdAndMapQueryResult(List<Statement> statements) {
        // Map and hash the account id before returning the result as StatementDTO
        List<StatementDTO> statementDTOs = new ArrayList<>();
        for (Statement statement : statements) {
            StatementDTO statementDTO = new StatementDTO();
            statementDTO.setId(statement.getId());
            statementDTO.setAccountId(HashingUtils.hash(statement.getAccountId()));
            statementDTO.setDateField(statement.getDateField());
            statementDTO.setAmount(statement.getAmount());
            statementDTOs.add(statementDTO);
        }
        return statementDTOs;
    }

}
