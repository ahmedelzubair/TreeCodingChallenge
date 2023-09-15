package sa.com.tree.account.statment.treecodingchallenge.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Statement {

    private Long id;
    private Long accountId;
    private String dateField;
    private String amount;
}