package sa.com.tree.account.statment.treecodingchallenge.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sa.com.tree.account.statment.treecodingchallenge.exception.HashingException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class HashingUtils {

    public static String hashAccountNumber(String accountNumber) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(accountNumber.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (Exception e) {
            log.error("[HashingUtils] Error while hashing account number: {} , error: {}", accountNumber, e.getMessage());
            throw new HashingException("Error while hashing account number : " + accountNumber);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
