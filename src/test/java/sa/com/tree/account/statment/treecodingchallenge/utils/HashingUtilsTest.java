package sa.com.tree.account.statment.treecodingchallenge.utils;

import org.junit.jupiter.api.Test;
import sa.com.tree.account.statment.treecodingchallenge.exception.HashingException;

import static org.junit.jupiter.api.Assertions.*;

class HashingUtilsTest {

    @Test
    void testHashNotNull() {
        String hash = HashingUtils.hash(123456L);
        assertNotNull(hash);
        assertEquals(64, hash.length()); // SHA-256 hash should have 64 characters
    }

    @Test
    void testHashZero() {
        String hash = HashingUtils.hash(0L);
        assertNotNull(hash);
        assertEquals(64, hash.length());
        assertEquals("5feceb66ffc86f38d952786c6d696c79c2dbc239dd4e91b46729d73a27fb57e9", hash);
    }

    @Test
    void testHashNegativeNumber() {
        String hash = HashingUtils.hash(-123456L);
        assertNotNull(hash);
        assertEquals(64, hash.length());
    }

    @Test
    void testHashNull() {
        assertThrows(HashingException.class, () -> {
            HashingUtils.hash(null);
        });
    }

    @Test
    void testHashingExceptionMessage() {
        try {
            HashingUtils.hash(null);
        } catch (HashingException e) {
            assertTrue(e.getMessage().contains("null"));
        }
    }

}
