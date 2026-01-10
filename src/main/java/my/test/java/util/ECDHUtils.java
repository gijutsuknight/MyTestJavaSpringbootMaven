package my.test.java.util;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECPoint;

import javax.crypto.KeyAgreement;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;

public class ECDHUtils {
    /**
     * Build PrivateKey from raw bytes (32 bytes)
     */
    public static PrivateKey privateKeyFromBytes(byte[] privBytes) throws Exception {
        KeyFactory kf = KeyFactory.getInstance("EC", "BC");
        ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256r1");
        ECPrivateKeySpec privSpec = new ECPrivateKeySpec(new BigInteger(1, privBytes), ecSpec);
        return kf.generatePrivate(privSpec);
    }

    /**
     * Build PublicKey from raw bytes (compressed/uncompressed)
     */
    public static PublicKey publicKeyFromBytes(byte[] pubBytes) throws Exception {
        KeyFactory kf = KeyFactory.getInstance("EC", "BC");
        ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256r1");

        ECPoint q = ecSpec.getCurve().decodePoint(pubBytes);
        ECPublicKeySpec pubSpec = new ECPublicKeySpec(q, ecSpec);
        return kf.generatePublic(pubSpec);
    }

    /**
     * Compute shared secret
     */
    public static byte[] computeSharedSecret(PrivateKey privKey, PublicKey pubKey) throws Exception {
        KeyAgreement ka = KeyAgreement.getInstance("ECDH", "BC");
        ka.init(privKey);
        ka.doPhase(pubKey, true);
        return ka.generateSecret();
    }
}
