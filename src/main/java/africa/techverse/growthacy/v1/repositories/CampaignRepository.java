package africa.techverse.growthacy.v1.repositories;

import africa.techverse.growthacy.v1.entities.Campaign;
import africa.techverse.growthacy.v1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    List<Campaign> findByCompany(User user);
}
