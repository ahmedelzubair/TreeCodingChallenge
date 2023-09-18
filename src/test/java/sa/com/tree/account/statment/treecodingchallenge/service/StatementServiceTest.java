package sa.com.tree.account.statment.treecodingchallenge.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sa.com.tree.account.statment.treecodingchallenge.dto.SearchCriteriaDTO;
import sa.com.tree.account.statment.treecodingchallenge.dto.StatementDTO;
import sa.com.tree.account.statment.treecodingchallenge.entity.Statement;
import sa.com.tree.account.statment.treecodingchallenge.exception.InvalidSearchCriteriaException;
import sa.com.tree.account.statment.treecodingchallenge.exception.StatementSearchException;
import sa.com.tree.account.statment.treecodingchallenge.repository.StatementRepository;
import sa.com.tree.account.statment.treecodingchallenge.utils.HashingUtils;
import sa.com.tree.account.statment.treecodingchallenge.validator.SearchCriteriaValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StatementServiceTest {

    @Mock
    private StatementRepository statementRepository;

    @Mock
    private SearchCriteriaValidator searchCriteriaValidator;

    @InjectMocks
    private StatementService statementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStatementsByCriteria_WithValidData() {
        SearchCriteriaDTO searchCriteriaDTO = SearchCriteriaDTO.builder()
                .accountId(1L)
                .fromDate("01.01.2023")
                .toDate("31.12.2023")
                .fromAmount("100.00")
                .toAmount("500.00")
                .build();

        Statement statement = Statement.builder()
                .id(1L)
                .accountId(1L)
                .dateField("01.01.2023")
                .amount("250.00")
                .build();

        List<Statement> mockStatements = Collections.singletonList(statement);
        when(statementRepository.findAllStatementsByAccountId(1L)).thenReturn(mockStatements);

        // Mock the validator to not throw any exceptions
        doNothing().when(searchCriteriaValidator).validate(searchCriteriaDTO);

        // Act
        Set<StatementDTO> result = statementService.getStatementsByCriteria(searchCriteriaDTO);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        StatementDTO statementDTO = result.iterator().next();
        assertEquals(statement.getId(), statementDTO.getId());
        assertEquals(HashingUtils.hash(statement.getAccountId()), statementDTO.getAccountId());
        assertEquals(statement.getDateField(), statementDTO.getStatementDate());
        assertEquals(statement.getAmount(), statementDTO.getAmount());
    }

    @Test
    void testGetStatementsByCriteria_WithInvalidData() {
        SearchCriteriaDTO searchCriteriaDTO = SearchCriteriaDTO.builder()
                .accountId(0L) // Invalid account ID
                .fromDate("01.01.2023")
                .toDate("31.12.2023")
                .fromAmount("100.00")
                .toAmount("500.00")
                .build();

        // Mock the validator to throw an exception
        doThrow(InvalidSearchCriteriaException.class).when(searchCriteriaValidator).validate(searchCriteriaDTO);

        // Act and Assert
        assertThrows(InvalidSearchCriteriaException.class, () -> statementService.getStatementsByCriteria(searchCriteriaDTO));
    }

    @Test
    void testGetStatementsByCriteria_WithEmptyResult() {
        SearchCriteriaDTO searchCriteriaDTO = SearchCriteriaDTO.builder()
                .accountId(1L)
                .fromDate("01.01.2023")
                .toDate("31.12.2023")
                .fromAmount("100.00")
                .toAmount("500.00")
                .build();

        // Mock the validator to not throw any exceptions
        doNothing().when(searchCriteriaValidator).validate(searchCriteriaDTO);

        // Mock an empty result from the repository
        when(statementRepository.findAllStatementsByAccountId(1L)).thenReturn(Collections.emptyList());

        // Act
        Set<StatementDTO> result = statementService.getStatementsByCriteria(searchCriteriaDTO);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetThreeMonthsBackStatement_WithEmptyResult() {
        Long accountId = 1L;

        // Mock an empty result from the repository
        when(statementRepository.findAllStatementsByAccountId(accountId)).thenReturn(Collections.emptyList());

        // Act
        Set<StatementDTO> result = statementService.getThreeMonthsBackStatement(accountId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetStatementsByCriteria_WithValidDataAndNoAmountRange() {
        SearchCriteriaDTO searchCriteriaDTO = SearchCriteriaDTO.builder()
                .accountId(1L)
                .fromDate("01.01.2023")
                .toDate("31.12.2023")
                .build();

        // Mock the validator to not throw any exceptions
        doNothing().when(searchCriteriaValidator).validate(searchCriteriaDTO);

        Statement statement = Statement.builder()
                .id(1L)
                .accountId(1L)
                .dateField("01.01.2023")
                .amount("250.00")
                .build();

        List<Statement> mockStatements = Collections.singletonList(statement);
        when(statementRepository.findAllStatementsByAccountId(1L)).thenReturn(mockStatements);

        // Act
        Set<StatementDTO> result = statementService.getStatementsByCriteria(searchCriteriaDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetStatementsByCriteria_WithValidDataAndInvalidDateRange() {
        SearchCriteriaDTO searchCriteriaDTO = SearchCriteriaDTO.builder()
                .accountId(1L)
                .fromDate("31.12.2023")
                .toDate("01.01.2023") // Invalid date range
                .fromAmount("100.00")
                .toAmount("500.00")
                .build();

        // Mock the validator to not throw any exceptions
        doNothing().when(searchCriteriaValidator).validate(searchCriteriaDTO);

        Statement statement = Statement.builder()
                .id(1L)
                .accountId(1L)
                .dateField("01.01.2023")
                .amount("250.00")
                .build();

        List<Statement> mockStatements = Collections.singletonList(statement);
        when(statementRepository.findAllStatementsByAccountId(1L)).thenReturn(mockStatements);

        // Act
        Set<StatementDTO> result = statementService.getStatementsByCriteria(searchCriteriaDTO);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetStatementsByCriteria_WithValidDataAndInvalidAmountRange() {
        SearchCriteriaDTO searchCriteriaDTO = SearchCriteriaDTO.builder()
                .accountId(1L)
                .fromDate("01.01.2023")
                .toDate("31.12.2023")
                .fromAmount("500.00")
                .toAmount("100.00") // Invalid amount range
                .build();

        // Mock the validator to not throw any exceptions
        doNothing().when(searchCriteriaValidator).validate(searchCriteriaDTO);

        Statement statement = Statement.builder()
                .id(1L)
                .accountId(1L)
                .dateField("01.01.2023")
                .amount("250.00")
                .build();

        List<Statement> mockStatements = Collections.singletonList(statement);
        when(statementRepository.findAllStatementsByAccountId(1L)).thenReturn(mockStatements);

        // Act
        Set<StatementDTO> result = statementService.getStatementsByCriteria(searchCriteriaDTO);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetThreeMonthsBackStatement_WithValidData() {
        Long accountId = 1L;

        // Three months ago
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        String threeMonthsAgoStr = threeMonthsAgo.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        Statement newerStatement = Statement.builder()
                .id(1L)
                .accountId(accountId)
                .dateField(threeMonthsAgoStr)
                .amount("200.00")
                .build();

        Statement olderStatement = Statement.builder()
                .id(2L)
                .accountId(accountId)
                .dateField(threeMonthsAgo.minusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .amount("300.00")
                .build();

        List<Statement> mockStatements = Arrays.asList(olderStatement, newerStatement);
        when(statementRepository.findAllStatementsByAccountId(accountId)).thenReturn(mockStatements);

        // Act
        Set<StatementDTO> result = statementService.getThreeMonthsBackStatement(accountId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        StatementDTO statementDTO = result.iterator().next();
        assertEquals(newerStatement.getId(), statementDTO.getId());
        assertEquals(HashingUtils.hash(newerStatement.getAccountId()), statementDTO.getAccountId());
        assertEquals(newerStatement.getDateField(), statementDTO.getStatementDate());
        assertEquals(newerStatement.getAmount(), statementDTO.getAmount());
    }

    @Test
    void testGetStatementsByCriteria_WithDifferentDateFormats() {
        SearchCriteriaDTO searchCriteriaDTO = SearchCriteriaDTO.builder()
                .accountId(1L)
                .fromDate("01.01.2023")
                .toDate("12/31/2023") // Different date format
                .fromAmount("100.00")
                .toAmount("500.00")
                .build();

        // Mock the validator to not throw any exceptions
        doNothing().when(searchCriteriaValidator).validate(searchCriteriaDTO);

        // Act and Assert
        assertThrows(StatementSearchException.class, () -> statementService.getStatementsByCriteria(searchCriteriaDTO));
    }

    @Test
    void testGetStatementsByCriteria_WithBoundaryValues() {
        LocalDate currentDate = LocalDate.now();
        String currentDateStr = currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        SearchCriteriaDTO searchCriteriaDTO = SearchCriteriaDTO.builder()
                .accountId(1L)
                .fromDate(currentDateStr) // Current date
                .toDate(currentDateStr)   // Current date
                .fromAmount("0.01")      // Minimum amount
                .toAmount("9999999.99")  // Maximum amount
                .build();

        Statement statement = Statement.builder()
                .id(1L)
                .accountId(1L)
                .dateField(currentDateStr)
                .amount("100.00")
                .build();

        List<Statement> mockStatements = Collections.singletonList(statement);
        when(statementRepository.findAllStatementsByAccountId(1L)).thenReturn(mockStatements);

        // Mock the validator to not throw any exceptions
        doNothing().when(searchCriteriaValidator).validate(searchCriteriaDTO);

        // Act
        Set<StatementDTO> result = statementService.getStatementsByCriteria(searchCriteriaDTO);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        StatementDTO statementDTO = result.iterator().next();
        assertEquals(statement.getId(), statementDTO.getId());
        assertEquals(HashingUtils.hash(statement.getAccountId()), statementDTO.getAccountId());
        assertEquals(statement.getDateField(), statementDTO.getStatementDate());
        assertEquals(statement.getAmount(), statementDTO.getAmount());
    }

    @Test
    void testGetStatementsByCriteria_WithRepositoryException() {
        SearchCriteriaDTO searchCriteriaDTO = SearchCriteriaDTO.builder()
                .accountId(1L)
                .fromDate("01.01.2023")
                .toDate("31.12.2023")
                .fromAmount("100.00")
                .toAmount("500.00")
                .build();

        // Mock the validator to not throw any exceptions
        doNothing().when(searchCriteriaValidator).validate(searchCriteriaDTO);

        // Mock the repository to throw an exception
        when(statementRepository.findAllStatementsByAccountId(1L)).thenThrow(RuntimeException.class);

        // Act and Assert
        assertThrows(StatementSearchException.class, () -> statementService.getStatementsByCriteria(searchCriteriaDTO));
    }

    @Test
    void testPerformance_GetStatementsByCriteria() {
        // Generate a large number of statements and create a SearchCriteriaDTO with valid criteria

        SearchCriteriaDTO searchCriteriaDTO = SearchCriteriaDTO.builder()
                .accountId(1L)
                .fromDate("01.01.2023")
                .toDate("31.12.2023")
                .fromAmount("100.00")
                .toAmount("500.00")
                .build();

        // Mock the validator to not throw any exceptions
        doNothing().when(searchCriteriaValidator).validate(searchCriteriaDTO);

        // Generate a large list of statements (e.g., thousands or more)
        List<Statement> mockStatements = generateLargeListOfStatements();

        // Mock the repository to return the large list of statements 10,000 items
        when(statementRepository.findAllStatementsByAccountId(1L)).thenReturn(mockStatements);

        // Measure and assert response time
        long startTime = System.currentTimeMillis();
        Set<StatementDTO> result = statementService.getStatementsByCriteria(searchCriteriaDTO);
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        assertTrue(executionTime < 1000);
    }

    private List<Statement> generateLargeListOfStatements() {
        // Generate a large list of statements for performance testing
        List<Statement> statements = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Statement statement = Statement.builder()
                    .id((long) i)
                    .accountId(1L)
                    .dateField("01.01.2023")
                    .amount("100.00")
                    .build();
            // Add more fields as needed
            statements.add(statement);
        }
        return statements;
    }
}

