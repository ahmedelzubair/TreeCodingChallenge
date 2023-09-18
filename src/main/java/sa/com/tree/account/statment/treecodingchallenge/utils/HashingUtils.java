package sa.com.tree.account.statment.treecodingchallenge.utils;

import lombok.extern.slf4j.Slf4j;
import sa.com.tree.account.statment.treecodingchallenge.exception.HashingException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Slf4j
public class HashingUtils {

    public static String hash(Long accountNumber) {
        try {
            if (accountNumber == null) {
                throw new HashingException("Account number is null");
            }
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(String.valueOf(accountNumber).getBytes(StandardCharsets.UTF_8));
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
