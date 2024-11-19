package africa.techverse.growthacy.v1.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Column(nullable = true)
    private String address;
}
