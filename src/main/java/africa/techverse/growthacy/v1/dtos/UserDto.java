package africa.techverse.growthacy.v1.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String type;
    private String status;
    private String referralCode;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private Boolean emailVerified;
    private Boolean phoneVerified;
}
