package my.test.java.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GenerateSharedSecretRequestDTO {
    @Schema(
            description = "Private key of sender in hex or base64",
            defaultValue = "de6811b341031365bf51205fcd3489d7b0b1e38d726c2b00c12af188017dd964"
    )
    public String privateKey;

    @Schema(
            description = "Peer public key in hex or base64 (compressed or uncompressed)",
            defaultValue = "04a1b2c3d4e5f67890123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef"
    )
    public String publicKey;

    @Schema(
            description = "Format of keys: hex or base64",
            defaultValue = "hex"
    )
    public String format;
}
