package sa.com.tree.account.statment.treecodingchallenge.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Account {
    private Long id;
    private String accountType;
    private String accountNumber;

}