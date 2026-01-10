package my.test.java.util;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CryptoUtils {

    /** ================= HEX METHODS ================= **/

    /**
     * Convert byte array to HEX string
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xFF));
        }
        return sb.toString();
    }

    /**
     * Convert HEX string to byte array
     */
    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        if (len % 2 != 0) {
            throw new IllegalArgumentException("Invalid HEX string length");
        }
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) (
                    (Character.digit(hex.charAt(i), 16) << 4)
                            + Character.digit(hex.charAt(i + 1), 16)
            );
        }
        return data;
    }

    /** ================= BASE64 METHODS ================= **/

    /**
     * Convert byte array to Base64 string
     */
    public static String bytesToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Convert Base64 string to byte array
     */
    public static byte[] base64ToBytes(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    /** ================= STRING METHODS ================= **/

    /**
     * Convert UTF-8 string to bytes
     */
    public static byte[] stringToBytes(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Convert bytes to UTF-8 string
     */
    public static String bytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
