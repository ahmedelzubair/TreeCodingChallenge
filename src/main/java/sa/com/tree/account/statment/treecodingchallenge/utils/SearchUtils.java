package sa.com.tree.account.statment.treecodingchallenge.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SearchUtils {

    private SearchUtils() {
    }

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static BigDecimal parseAmount(String amount) {
        return new BigDecimal(amount);
    }

}
