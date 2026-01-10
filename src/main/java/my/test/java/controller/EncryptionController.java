package my.test.java.controller;

import my.test.java.constant.EncryptionAlgorithm;
import my.test.java.constant.SecretKeyAlgorithm;
import my.test.java.dto.AesCbc256DecryptRequestDTO;
import my.test.java.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/encryption")
public class EncryptionController {
    @Autowired
    EncryptionService encryptionService;

    @PostMapping("aes-cbc-256/decrypt")
    public String aesCbc256Decrypt(@RequestBody AesCbc256DecryptRequestDTO aesCbc256DecryptRequestDTO) throws Exception {
        System.out.println("aesCbc256DecryptRequestDTO" + aesCbc256DecryptRequestDTO);
        SecretKey secretKey = encryptionService.keyFromBase64("ede76c73038b187c7d2b8ca072a6a9858c7406fb9111654822dba58461923d81", SecretKeyAlgorithm.AES);
        return encryptionService.decrypt(EncryptionAlgorithm.AES_CBC_PKCS5, aesCbc256DecryptRequestDTO.getPassword(), aesCbc256DecryptRequestDTO.getIv(), secretKey);
    }
}
