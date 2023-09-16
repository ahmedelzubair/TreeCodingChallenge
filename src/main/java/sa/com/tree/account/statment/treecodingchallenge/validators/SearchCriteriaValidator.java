package sa.com.tree.account.statment.treecodingchallenge.validators;

import org.springframework.stereotype.Component;
import sa.com.tree.account.statment.treecodingchallenge.dto.SearchCriteriaDTO;
import sa.com.tree.account.statment.treecodingchallenge.exception.InvalidSearchCriteriaException;

@Component
public class SearchCriteriaValidator {


    public void validate(SearchCriteriaDTO searchCriteriaDTO) {
        // search criteria must be provided
        if (searchCriteriaDTO == null)
            throw new InvalidSearchCriteriaException("Invalid Search Criteria");

        // account id must be provided
        if (searchCriteriaDTO.getAccountId() == null || searchCriteriaDTO.getAccountId() <= 0)
            throw new InvalidSearchCriteriaException("Please specify account id");

        // if user provide from date then to date must be provided
        if (searchCriteriaDTO.getFromDate() != null && searchCriteriaDTO.getToDate() == null)
            throw new InvalidSearchCriteriaException("Please specify to date");

        // if user provide to date then from date must be provided
        if (searchCriteriaDTO.getToDate() != null && searchCriteriaDTO.getFromDate() == null)
            throw new InvalidSearchCriteriaException("Please specify from date");

        // if user provide from amount then to amount must be provided
        if (searchCriteriaDTO.getFromAmount() != null && searchCriteriaDTO.getToAmount() == null)
            throw new InvalidSearchCriteriaException("Please specify to amount");

        // if user provide to amount then from amount must be provided
        if (searchCriteriaDTO.getToAmount() != null && searchCriteriaDTO.getFromAmount() == null)
            throw new InvalidSearchCriteriaException("Please specify from amount");

    }

}
