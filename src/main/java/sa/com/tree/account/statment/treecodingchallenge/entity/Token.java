package sa.com.tree.account.statment.treecodingchallenge.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sa.com.tree.account.statment.treecodingchallenge.dto.TokenType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    private Integer id;
    private String token;
    private TokenType tokenType = TokenType.BEARER;
    private boolean revoked;
    private boolean expired;
    private User user;

}
