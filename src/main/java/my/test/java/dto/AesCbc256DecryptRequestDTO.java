package my.test.java.dto;

import lombok.Data;

@Data
public class AesCbc256DecryptRequestDTO {
    private String password;
    private String iv;
}
