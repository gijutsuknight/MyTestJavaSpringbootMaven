package my.test.java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GetEcdhKeyPairHexResponseDTO {
    private String privateKeyRawHex;          // 32 bytes
    private String publicKeyUncompressedHex;  // 65 bytes
    private String publicKeyCompressedHex;
}
