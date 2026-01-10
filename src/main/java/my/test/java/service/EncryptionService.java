package my.test.java.service;

import my.test.java.constant.EncryptionAlgorithm;
import my.test.java.constant.SecretKeyAlgorithm;
import my.test.java.util.CryptoUtils;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class EncryptionService {
    public SecretKey keyFromBase64(String base64Key, SecretKeyAlgorithm secretKeyAlgorithm) {
        byte[] decodedKey = CryptoUtils.hexToBytes(base64Key);
//        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, secretKeyAlgorithm.value());
    }

    // Decrypt ciphertext
    public String decrypt(EncryptionAlgorithm encryptionAlgorithm, String cipherTextBase64, String ivBase64, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm.getTransformation());
        byte[] iv = CryptoUtils.hexToBytes(ivBase64);

        System.out.println("ivBase64:" + ivBase64);
        System.out.println("AAA:" + iv.length);
//        byte[] iv = Base64.getDecoder().decode(ivBase64);
//
        byte[] cipherText = Base64.getDecoder().decode(cipherTextBase64);

        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

        byte[] decrypted = cipher.doFinal(cipherText);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    // Helper class to hold encrypted data
    private static class EncryptedData {
        String cipherText;
        String iv;

        EncryptedData(String cipherText, String iv) {
            this.cipherText = cipherText;
            this.iv = iv;
        }
    }
}
