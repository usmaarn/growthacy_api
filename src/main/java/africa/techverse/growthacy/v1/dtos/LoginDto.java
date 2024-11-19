package africa.techverse.growthacy.v1.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
