package sa.com.tree.account.statment.treecodingchallenge.entity;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Statement {

    private Long id;
    private Long accountId;
    private String dateField;
    private String amount;
}