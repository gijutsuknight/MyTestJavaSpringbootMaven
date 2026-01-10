package my.test.java.controller;

import my.test.java.dto.*;
import my.test.java.service.ECDHBouncyCastleService;
import my.test.java.service.ECDHService;
import my.test.java.util.CryptoUtils;
import my.test.java.util.ECDHUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.ECPublicKey;
import java.util.Base64;

@RestController
@RequestMapping("/ecdh")
public class ECDHKeyExchangeController {
    @Autowired
    private ECDHService ecdhService;
    @Autowired
    private ECDHBouncyCastleService ecdhBouncyCastleService;

    @GetMapping("/encoded/base64")
    public ECDHGetKeyPairResponseDTO getEncodedKeyPairBase64() throws NoSuchAlgorithmException {

        KeyPair keyPair = null;
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
        kpg.initialize(256);
        keyPair = kpg.generateKeyPair();


        return new ECDHGetKeyPairResponseDTO(
                Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()),
                Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded())
        );
    }

    @GetMapping("/encoded/hex")
    public ECDHGetKeyPairResponseDTO getEncodedKeyPairHex() throws NoSuchAlgorithmException {

        KeyPair keyPair = null;
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
        kpg.initialize(256);
        keyPair = kpg.generateKeyPair();

        return new ECDHGetKeyPairResponseDTO(
                CryptoUtils.bytesToHex(keyPair.getPublic().getEncoded()),
                CryptoUtils.bytesToHex(keyPair.getPrivate().getEncoded())
        );
    }

    @GetMapping("/hex")
    public GetEcdhKeyPairHexResponseDTO getEcdhKeyPairHex() throws GeneralSecurityException {
        ECDHBouncyCastleService.RawECKeys rawECKeys = ecdhBouncyCastleService.generateRawECDHKeys();
        return new GetEcdhKeyPairHexResponseDTO(
                CryptoUtils.bytesToHex(rawECKeys.getPrivateKeyRaw()),
                CryptoUtils.bytesToHex(rawECKeys.getPublicKeyUncompressed()),
                CryptoUtils.bytesToHex(rawECKeys.getPublicKeyCompressed())
        );
    }

    @PostMapping("/shared-secret")
    public GenerateSharedSecretResponseDTO generateSharedSecret(@RequestBody GenerateSharedSecretRequestDTO req) throws Exception {
        // Convert keys based on format
        byte[] privBytes = "base64".equalsIgnoreCase(req.format)
                ? CryptoUtils.base64ToBytes(req.privateKey)
                : CryptoUtils.hexToBytes(req.privateKey);

        byte[] pubBytes = "base64".equalsIgnoreCase(req.format)
                ? CryptoUtils.base64ToBytes(req.publicKey)
                : CryptoUtils.hexToBytes(req.publicKey);

        PrivateKey privKey = ECDHUtils.privateKeyFromBytes(privBytes);
        PublicKey pubKey = ECDHUtils.publicKeyFromBytes(pubBytes);
        byte[] sharedSecret = ECDHUtils.computeSharedSecret(privKey, pubKey);

        return new GenerateSharedSecretResponseDTO(
                CryptoUtils.bytesToHex(sharedSecret),
                CryptoUtils.bytesToBase64(sharedSecret)
        );
    }

    @PostMapping("/generate-shared-secret-by-hex")
    public String generateSharedSecretByHex(@RequestBody ECDHGenerateSharedSecretRequestDTO ecdhGenerateSharedSecretRequestDTO) throws Exception {

        KeyAgreement ka = KeyAgreement.getInstance("ECDH");

        ka.init(ecdhService.privateKeyFromHex(ecdhGenerateSharedSecretRequestDTO.getPrivateKey()));
        ka.doPhase(ecdhService.publicKeyFromHex(ecdhGenerateSharedSecretRequestDTO.getPublicKey()), true);
        byte[] sharedSecret = ka.generateSecret();


        return CryptoUtils.bytesToHex(sharedSecret);
    }
}
