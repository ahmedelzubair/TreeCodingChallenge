package sa.com.tree.account.statment.treecodingchallenge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCriteriaDTO {

    private Long accountId;
    private String fromDate;
    private String toDate;
    private String fromAmount;
    private String toAmount;

}
