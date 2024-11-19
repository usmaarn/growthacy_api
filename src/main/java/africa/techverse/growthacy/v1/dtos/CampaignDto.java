package africa.techverse.growthacy.v1.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDto {
    private Long id;
    private String name;
    private String description;
    private UserDto company;
    private int ambassadors;
    private String status;
    private LocalDateTime createdAt;
}
