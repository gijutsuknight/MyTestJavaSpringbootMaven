package my.test.java.service;

import org.bouncycastle.jce.ECPointUtil;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;

@Service
public class ECDHService {

    public PrivateKey privateKeyFromHex(String hexPrivateKey) throws Exception {
        // Convert hex string to BigInteger
        BigInteger s = new BigInteger(hexPrivateKey, 16);

        // Use secp256k1 curve (or secp256r1 if that's your curve)
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("EC");
        parameters.init(new ECGenParameterSpec("secp256k1")); // change curve if needed
        ECParameterSpec ecSpec = parameters.getParameterSpec(ECParameterSpec.class);

        // Build private key spec
        ECPrivateKeySpec privateKeySpec = new ECPrivateKeySpec(s, ecSpec);

        KeyFactory kf = KeyFactory.getInstance("EC");
        return kf.generatePrivate(privateKeySpec);
    }

    public PublicKey publicKeyFromHex(String hexPublicKey) throws Exception {
        byte[] pubBytes = hexStringToByteArray(hexPublicKey);

        AlgorithmParameters parameters = AlgorithmParameters.getInstance("EC");
        parameters.init(new ECGenParameterSpec("secp256k1"));
        ECParameterSpec ecSpec = parameters.getParameterSpec(ECParameterSpec.class);

        // Decode EC point
        ECPoint point = ECPointUtil.decodePoint(ecSpec.getCurve(), pubBytes);

        ECPublicKeySpec pubSpec = new ECPublicKeySpec(point, ecSpec);
        KeyFactory kf = KeyFactory.getInstance("EC");
        return kf.generatePublic(pubSpec);
    }

    // Helper
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }


}
