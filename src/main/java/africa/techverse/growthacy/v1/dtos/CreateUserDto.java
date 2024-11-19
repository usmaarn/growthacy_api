package africa.techverse.growthacy.v1.dtos;


import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateUserDto {
    @NotBlank
    @Length(min = 3, max = 50)
    private String name;

    @Email(message = "Email is not valid")
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^0[789][01][0-9]{8}$", message = "Invalid phone number")
    private String phone;

    @NotBlank
    @Digits(integer = 2, fraction = 0)
    private Integer type;

    @NotNull
    @Length(min = 8)
    private String password;
}
