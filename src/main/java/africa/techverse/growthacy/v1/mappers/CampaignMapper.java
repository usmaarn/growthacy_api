package africa.techverse.growthacy.v1.mappers;

import africa.techverse.growthacy.v1.dtos.CampaignDto;
import africa.techverse.growthacy.v1.entities.Campaign;
import africa.techverse.growthacy.v1.enums.Status;

public class CampaignMapper {
    public static CampaignDto toDto(Campaign campaign) {
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setId(campaign.getId());
        campaignDto.setName(campaign.getName());
        campaignDto.setStatus(campaign.getStatus() == Status.ACTIVE.getValue() ? "ACTIVE" : "INACTIVE");
        campaignDto.setDescription(campaign.getDescription());
        campaignDto.setCompany(UserMapper.toDto(campaign.getCompany()));
        campaignDto.setCreatedAt(campaign.getCreatedAt());
        campaignDto.setAmbassadors(campaign.getAmbassadors().size());
        return campaignDto;
    }
}
