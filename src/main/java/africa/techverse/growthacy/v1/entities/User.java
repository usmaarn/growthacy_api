package africa.techverse.growthacy.v1.entities;


import africa.techverse.growthacy.v1.enums.UserStatus;
import africa.techverse.growthacy.v1.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="users")
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "users_email_unique", columnNames = "email"),
                @UniqueConstraint(name = "users_phone_unique", columnNames = "phone"),
                @UniqueConstraint(name = "users_referral_code_unique", columnNames = "referral_code")
        }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String phone;

    @Column(name = "referral_code", length = 100)
    private String referralCode;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int type;

    private int status = UserStatus.ACTIVE.getValue();

    private LocalDateTime emailVerifiedAt;

    private LocalDateTime phoneVerifiedAt;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime deletedAt;

    @Embedded
    private Company company;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private List<Campaign> campaigns;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (status == 0) status = UserStatus.ACTIVE.getValue();
        if (type == UserType.AMBASSADOR.getValue()) referralCode = UUID.randomUUID().toString();
    }
}
