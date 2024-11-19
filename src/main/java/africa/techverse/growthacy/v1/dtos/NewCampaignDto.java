package africa.techverse.growthacy.v1.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewCampaignDto {
    @NotBlank
    @Length(min = 10, max = 100)
    private String name;

    @NotBlank
    @Length(min = 100)
    private String description;

    @NotBlank
    @Pattern(regexp = "^[0-9]{2,}$", message = "price must be numeric")
    private String reward;
}
