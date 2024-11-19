package africa.techverse.growthacy.v1.entities;


import africa.techverse.growthacy.v1.dtos.CampaignDto;
import africa.techverse.growthacy.v1.enums.Status;
import africa.techverse.growthacy.v1.enums.UserType;
import africa.techverse.growthacy.v1.mappers.CampaignMapper;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "campaigns")
@Table(
        name = "campaigns",
        uniqueConstraints = {

        }
)
public class Campaign {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int status = Status.ACTIVE.getValue();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private User company;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "campaign_ambassador",
            joinColumns = @JoinColumn(name = "campaign_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ambassador_id", referencedColumnName = "id")
    )
    private List<User> ambassadors;

    @PrePersist
    protected void onCreate() {
        if (company.getType() != UserType.COMPANY.getValue()){
            throw new IllegalStateException("User must be a company");
        }
        if (ambassadors == null) ambassadors = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = Status.ACTIVE.getValue();
    }

    public void addAmbassador(User ambassador) {
        if (ambassadors == null) ambassadors = new ArrayList<>();
        this.ambassadors.add(ambassador);
    }

    public CampaignDto toDto() {
        return CampaignMapper.toDto(this);
    }
}