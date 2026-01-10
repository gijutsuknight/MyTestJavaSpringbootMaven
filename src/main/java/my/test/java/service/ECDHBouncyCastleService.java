package my.test.java.service;

import lombok.Data;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;

import java.math.BigInteger;
import java.security.GeneralSecurityException;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECPoint;
import org.springframework.stereotype.Service;


import javax.crypto.KeyAgreement;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;

@Service
public class ECDHBouncyCastleService {
    @Data
    // 🔑 Container for raw keys
    public static class RawECKeys {
        public final byte[] privateKeyRaw;          // 32 bytes
        public final byte[] publicKeyUncompressed;  // 65 bytes
        public final byte[] publicKeyCompressed;    // 33 bytes

        public RawECKeys(byte[] priv, byte[] pubU, byte[] pubC) {
            this.privateKeyRaw = priv;
            this.publicKeyUncompressed = pubU;
            this.publicKeyCompressed = pubC;
        }
    }

    /**
     * Generate raw ECDH keys (secp256r1)
     */
    public RawECKeys generateRawECDHKeys() throws GeneralSecurityException {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", "BC");
        kpg.initialize(new ECGenParameterSpec("secp256r1"));
        KeyPair kp = kpg.generateKeyPair();

        ECPrivateKey privateKey = (ECPrivateKey) kp.getPrivate();
        ECPublicKey publicKey = (ECPublicKey) kp.getPublic();

        // Raw private key (32 bytes)
        byte[] privateRaw = toFixedLength(privateKey.getD().toByteArray(), 32);

        // Raw public keys
        ECPoint q = publicKey.getQ();
        byte[] publicUncompressed = q.getEncoded(false);
        byte[] publicCompressed = q.getEncoded(true);

        return new RawECKeys(privateRaw, publicUncompressed, publicCompressed);
    }

    // 🔒 Ensure private key is exactly 32 bytes
    private byte[] toFixedLength(byte[] src, int length) {
        if (src.length == length) return src;

        if (src.length > length) {
            return Arrays.copyOfRange(src, src.length - length, src.length);
        }

        byte[] dst = new byte[length];
        System.arraycopy(src, 0, dst, length - src.length, src.length);
        return dst;
    }

    /**
     * Convert raw 32-byte private key to PrivateKey object
     */
    public static PrivateKey privateKeyFromBytes(byte[] privBytes) throws Exception {
        KeyFactory kf = KeyFactory.getInstance("EC", "BC");
        ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256r1");
        ECPrivateKeySpec privSpec = new ECPrivateKeySpec(new BigInteger(1, privBytes), ecSpec);
        return kf.generatePrivate(privSpec);
    }

    /**
     * Convert raw public key (compressed/uncompressed) to PublicKey object
     */
    public static PublicKey publicKeyFromBytes(byte[] pubBytes) throws Exception {
        KeyFactory kf = KeyFactory.getInstance("EC", "BC");
        ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256r1");

        ECPoint point = ecSpec.getCurve().decodePoint(pubBytes); // works for compressed/uncompressed
        ECPublicKeySpec pubSpec = new ECPublicKeySpec(point, ecSpec);
        return kf.generatePublic(pubSpec);
    }

    /**
     * Compute shared secret (raw bytes) using private key + peer public key
     */
    public static byte[] computeSharedSecret(PrivateKey privKey, PublicKey pubKey) throws Exception {
        KeyAgreement ka = KeyAgreement.getInstance("ECDH", "BC");
        ka.init(privKey);
        ka.doPhase(pubKey, true);
        return ka.generateSecret(); // raw shared secret
    }
}
