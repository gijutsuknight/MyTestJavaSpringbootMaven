package my.test.java.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(description = "ECDH shared secret result")
public class GenerateSharedSecretResponseDTO {
    @Schema(
            description = "Shared secret as hex string",
            example = "b5a3f0c51234abcd56789ef0123456789abcdef0123456789abcdef01234567"
    )
    public String sharedSecretHex;

    @Schema(
            description = "Shared secret as Base64 string",
            example = "taPDxRMTq83VZie8BI0Vni0xEjY="
    )
    public String sharedSecretBase64;
}
