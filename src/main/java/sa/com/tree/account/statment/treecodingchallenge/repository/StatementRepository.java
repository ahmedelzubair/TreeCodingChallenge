package sa.com.tree.account.statment.treecodingchallenge.repository;

import sa.com.tree.account.statment.treecodingchallenge.entity.Statement;

import java.util.List;

public interface StatementRepository {

    List<Statement> findAllStatementsByAccountId(Long accountId);
}
