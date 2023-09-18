package sa.com.tree.account.statment.treecodingchallenge.utils;

import org.apache.commons.lang3.StringUtils;
import sa.com.tree.account.statment.treecodingchallenge.dto.SearchCriteriaDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SearchUtils {


    public static boolean isEmptySearchCriteria(SearchCriteriaDTO searchCriteriaDTO) {
        return isEmpty(searchCriteriaDTO.getFromDate()) && isEmpty(searchCriteriaDTO.getToDate())
                && isEmpty(searchCriteriaDTO.getFromAmount()) && isEmpty(searchCriteriaDTO.getToAmount());
    }

    public static boolean isEmpty(String value) {
        return StringUtils.isEmpty(value);
    }

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static BigDecimal parseAmount(String amount) {
        return new BigDecimal(amount);
    }

}
