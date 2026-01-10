package my.test.java.dto;

import lombok.Data;

@Data
public class ECDHGenerateSharedSecretRequestDTO {
    private String publicKey;
    private String privateKey;
}
