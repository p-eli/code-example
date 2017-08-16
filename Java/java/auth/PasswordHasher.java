package auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Project: Airport
 * File: PasswordHasher.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
public class PasswordHasher {

    public static String Hash(String login, String password) {
        String both = String.format("%s|%s", login, password);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(both.getBytes());
            return toHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            return both;
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
