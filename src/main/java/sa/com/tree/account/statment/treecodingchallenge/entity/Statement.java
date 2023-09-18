package sa.com.tree.account.statment.treecodingchallenge.entity;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Statement {

    private Long id;
    private Long accountId;
    private String dateField;
    private String amount;
}