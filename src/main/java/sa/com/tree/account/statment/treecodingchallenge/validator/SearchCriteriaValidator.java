package sa.com.tree.account.statment.treecodingchallenge.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import sa.com.tree.account.statment.treecodingchallenge.dto.SearchCriteriaDTO;
import sa.com.tree.account.statment.treecodingchallenge.exception.InvalidSearchCriteriaException;

@Component
public class SearchCriteriaValidator {


    public void validate(SearchCriteriaDTO searchCriteriaDTO) {
        // search criteria must be provided
        if (searchCriteriaDTO == null)
            throw new InvalidSearchCriteriaException("Invalid search criteria");

        // account id must be provided
        if (searchCriteriaDTO.getAccountId() == null || searchCriteriaDTO.getAccountId() <= 0)
            throw new InvalidSearchCriteriaException("Please specify account id");

        // if user provide from date then to date must be provided
        if (StringUtils.isNotEmpty(searchCriteriaDTO.getFromDate()) && (StringUtils.isEmpty(searchCriteriaDTO.getToDate())))
            throw new InvalidSearchCriteriaException("Please specify to date");

        // if user provide to date then from date must be provided
        if (StringUtils.isNotEmpty(searchCriteriaDTO.getToDate()) && (StringUtils.isEmpty(searchCriteriaDTO.getFromDate())))
            throw new InvalidSearchCriteriaException("Please specify from date");

        // if user provide from amount then to amount must be provided
        if (StringUtils.isNotEmpty(searchCriteriaDTO.getFromAmount()) && StringUtils.isEmpty(searchCriteriaDTO.getToAmount()))
            throw new InvalidSearchCriteriaException("Please specify to amount");

        // if user provide to amount then from amount must be provided
        if (StringUtils.isNotEmpty(searchCriteriaDTO.getToAmount()) && StringUtils.isEmpty(searchCriteriaDTO.getFromAmount()))
            throw new InvalidSearchCriteriaException("Please specify from amount");

    }

}
