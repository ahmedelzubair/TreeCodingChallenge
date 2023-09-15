package sa.com.tree.account.statment.treecodingchallenge.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApiResponse {

    private int status;
    private String message;
    private Object data;
    private LocalDateTime timestamp;

}
