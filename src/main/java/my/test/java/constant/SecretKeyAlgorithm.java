package my.test.java.constant;

public enum SecretKeyAlgorithm {

    AES("AES"),
    HMAC_SHA256("HmacSHA256"),
    HMAC_SHA512("HmacSHA512");

    private final String value;

    SecretKeyAlgorithm(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
