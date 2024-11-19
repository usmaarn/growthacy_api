package africa.techverse.growthacy.v1.repositories;

import africa.techverse.growthacy.v1.entities.Campaign;
import africa.techverse.growthacy.v1.entities.User;
import africa.techverse.growthacy.v1.enums.Status;
import africa.techverse.growthacy.v1.enums.UserType;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Locale;


@SpringBootTest
class CampaignRepositoryTest {
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private UserRepository userRepository;

    Faker faker = new Faker(new Locale("en-US"));

    private User geenrateUser(int type){
        User user = User.builder()
                .name(faker.name().fullName())
                .email(faker.internet().safeEmailAddress())
                .phone(faker.phoneNumber().phoneNumber())
                .type(type)
                .password("test")
                .build();
        return userRepository.save(user);
    }

    private Campaign generateCampaign(User company, Integer status) {
        Campaign campaign = Campaign.builder()
                .name(faker.lorem().sentence())
                .description(faker.lorem().paragraph())
                .status(status)
                .company(company)
                .build();
        return campaignRepository.save(campaign);
    }

    @Test
    public void createCampaign() {
        User company = geenrateUser(UserType.COMPANY.getValue());
        Campaign campaign = generateCampaign(company, Status.ACTIVE.getValue());
        System.out.println(campaign);
    }

    @Test
    public void getCampaignsByCompany(){
        User company1 = geenrateUser(UserType.COMPANY.getValue());
        User company2 = geenrateUser(UserType.COMPANY.getValue());

        generateCampaign(company1, Status.ACTIVE.getValue());
        generateCampaign(company2, Status.ACTIVE.getValue());
        generateCampaign(company1, Status.INACTIVE.getValue());
        generateCampaign(company2, Status.INACTIVE.getValue());
        generateCampaign(company1, Status.ACTIVE.getValue());

        List<Campaign> campaigns1 = campaignRepository.findByCompany(company1);
        List<Campaign> campaigns2 = campaignRepository.findByCompany(company2);
        System.out.println("Company 1 Campaigns = "+campaigns1);
        System.out.println("Company 2 Campaigns = "+campaigns1);
    }

    @Test
    public void findAllPagination(){
        Pageable firstPageWithThreeRecords = PageRequest.of(0, 3);
        Pageable secondPageWithTwoRecords = PageRequest.of(1, 2);

        Page<Campaign> campaigns = campaignRepository.findAll(secondPageWithTwoRecords);
        System.out.println("campaigns = "+ campaigns.getContent());

        System.out.println("Total elements = "+ campaigns.getTotalElements());
        System.out.println("Total pages = "+ campaigns.getTotalPages());
        System.out.println("Page = "+ campaigns);
    }

    @Test
    public void findAllSorting() {
        Pageable sortByTitle = PageRequest.of(0, 2, Sort.by("name"));
        Pageable sortByCreatedAt = PageRequest.of(0, 2, Sort.by("createdAt").descending());

        List<Campaign> campaigns = campaignRepository.findAll(sortByTitle).getContent().stream().map(c -> c).toList();
        System.out.println("campaigns = "+ campaigns);
    }

    @Test
    public void addAmbassadorToCampaign() {
        User ambassador1 = geenrateUser(UserType.AMBASSADOR.getValue());
        User ambassador2 = geenrateUser(UserType.AMBASSADOR.getValue());
        User ambassador3 = geenrateUser(UserType.AMBASSADOR.getValue());
        User company = geenrateUser(UserType.COMPANY.getValue());

        Campaign campaign = generateCampaign(company, Status.ACTIVE.getValue());
        campaign.addAmbassador(ambassador1);
        campaign.addAmbassador(ambassador2);
        campaign.addAmbassador(ambassador3);

        campaign = campaignRepository.save(campaign);

        System.out.println("campaign ambassadors = "+ campaign.getAmbassadors());
    }
}