package my.test.java.constant;

/**
 * Enumeration of common encryption algorithms and their transformations
 * for use with javax.crypto.Cipher.getInstance()
 */
public enum EncryptionAlgorithm {
    // AES algorithms
    AES_CBC_PKCS5("AES/CBC/PKCS5Padding"),
    AES_CBC_PKCS7("AES/CBC/PKCS7Padding"),
    AES_GCM_NO_PADDING("AES/GCM/NoPadding"),
    AES_ECB_PKCS5("AES/ECB/PKCS5Padding"),
    AES_CTR_NO_PADDING("AES/CTR/NoPadding"),
    
    // RSA algorithms
    RSA_ECB_PKCS1("RSA/ECB/PKCS1Padding"),
    RSA_ECB_OAEP("RSA/ECB/OAEPWithSHA-256AndMGF1Padding"),
    RSA_NONE_PKCS1("RSA/None/PKCS1Padding"),
    
    // DES algorithms
    DES_CBC_PKCS5("DES/CBC/PKCS5Padding"),
    DES_ECB_PKCS5("DES/ECB/PKCS5Padding"),
    
    // Triple DES algorithms
    DESEDE_CBC_PKCS5("DESede/CBC/PKCS5Padding"),
    DESEDE_ECB_PKCS5("DESede/ECB/PKCS5Padding"),
    
    // Blowfish algorithms
    BLOWFISH_CBC_PKCS5("Blowfish/CBC/PKCS5Padding"),
    BLOWFISH_ECB_PKCS5("Blowfish/ECB/PKCS5Padding");
    
    private final String transformation;
    
    EncryptionAlgorithm(String transformation) {
        this.transformation = transformation;
    }
    
    /**
     * Returns the transformation string for use with Cipher.getInstance()
     * @return the transformation string (e.g., "AES/CBC/PKCS5Padding")
     */
    public String getTransformation() {
        return transformation;
    }
    
    /**
     * Returns the transformation string (same as getTransformation())
     * Allows direct usage: Cipher.getInstance(EncryptionAlgorithm.AES_CBC_PKCS5)
     * @return the transformation string
     */
    @Override
    public String toString() {
        return transformation;
    }
}
