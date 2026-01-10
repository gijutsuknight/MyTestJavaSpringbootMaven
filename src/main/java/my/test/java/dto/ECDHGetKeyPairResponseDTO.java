package my.test.java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ECDHGetKeyPairResponseDTO {
    private String publicKey;
    private String privateKey;
}
