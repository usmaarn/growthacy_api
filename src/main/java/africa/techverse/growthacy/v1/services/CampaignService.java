package africa.techverse.growthacy.v1.services;


import africa.techverse.growthacy.v1.dtos.NewCampaignDto;
import africa.techverse.growthacy.v1.dtos.ResponseDto;
import africa.techverse.growthacy.v1.entities.Campaign;
import africa.techverse.growthacy.v1.entities.User;
import africa.techverse.growthacy.v1.enums.UserType;
import africa.techverse.growthacy.v1.exceptions.HttpException;
import africa.techverse.growthacy.v1.mappers.CampaignMapper;
import africa.techverse.growthacy.v1.repositories.CampaignRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CampaignService {
    private final CampaignRepository campaignRepository;

    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public ResponseDto getCampaigns(User user) {
        List<Campaign> campaigns = campaignRepository.findByCompany(user);
        return ResponseDto.builder()
                .data(Map.of("list", campaigns.stream().map(CampaignMapper::toDto).toList()))
                .build();
    }

    public ResponseDto createCampaign(User user, NewCampaignDto newCampaignDto) {
        if (user.getType() != UserType.COMPANY.getValue()) {
            throw new HttpException(403, "FORBIDDEN");
        };
        Campaign campaign = Campaign.builder()
                .name(newCampaignDto.getName())
                .description(newCampaignDto.getDescription())
                .company(user)
                .build();
        campaign = campaignRepository.save(campaign);
        return ResponseDto.builder()
                .status(HttpStatus.CREATED)
                .data(campaign.toDto())
                .build();
    }

    public ResponseDto getCampaign(Long id) {
        Campaign campaign = this.campaignRepository.findById(id).orElseThrow(
                () -> new HttpException(404, "Campaign not found")
        );
        return ResponseDto.builder()
                .data(campaign.toDto())
                .build();
    }

    public ResponseDto updateCampaign(Long id, NewCampaignDto campaignDto) {
        Campaign campaign = this.campaignRepository.findById(id).orElseThrow(
                () -> new HttpException(404, "Campaign not found")
        );

        BeanUtils.copyProperties(campaignDto, campaign, "id");

        return ResponseDto.builder()
                .data(campaignRepository.save(campaign).toDto())
                .build();
    }

    public void deleteCampaign(User user, Long id) {
        Campaign campaign = this.campaignRepository.findById(id).orElseThrow(
                () -> new HttpException(404, "Campaign not found")
        );
        if (!campaign.getCompany().getId().equals(user.getId())) throw new HttpException(403, "FORBIDDEN");
        campaignRepository.delete(campaign);
    }
}
