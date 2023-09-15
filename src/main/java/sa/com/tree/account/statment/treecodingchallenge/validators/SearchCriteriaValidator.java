package sa.com.tree.account.statment.treecodingchallenge.validators;

import org.springframework.stereotype.Component;
import sa.com.tree.account.statment.treecodingchallenge.dto.SearchCriteriaDTO;

@Component
public class SearchCriteriaValidator {


    public void validate(SearchCriteriaDTO searchCriteriaDTO) {
        if (searchCriteriaDTO == null)
            throw new RuntimeException("Invalid Search Criteria");

        if (searchCriteriaDTO.getAccountId() == null || searchCriteriaDTO.getAccountId() <= 0)
            throw new RuntimeException("Invalid Account Id");
    }

}
