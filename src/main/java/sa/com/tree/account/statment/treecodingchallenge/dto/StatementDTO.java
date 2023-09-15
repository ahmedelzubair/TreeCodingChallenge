package sa.com.tree.account.statment.treecodingchallenge.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatementDTO {

    private Long id;
    private String accountId;
    private String dateField;
    private String amount;
}