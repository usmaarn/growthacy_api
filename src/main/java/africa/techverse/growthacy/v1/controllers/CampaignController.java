package africa.techverse.growthacy.v1.controllers;


import africa.techverse.growthacy.v1.dtos.NewCampaignDto;
import africa.techverse.growthacy.v1.dtos.ResponseDto;
import africa.techverse.growthacy.v1.entities.User;
import africa.techverse.growthacy.v1.services.CampaignService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto getCampaigns(@RequestAttribute("user") User user) {
        return this.campaignService.getCampaigns(user);
    }

    @PostMapping
    public ResponseDto createCampaign(
            @RequestAttribute("user") User user,
            @Valid @RequestBody NewCampaignDto campaignDto
    ) {
       return this.campaignService.createCampaign(user, campaignDto);
    }

    @GetMapping("/{campaignId}")
    public ResponseDto getCampaign(@PathVariable("campaignId") Long campaignId) {
        return this.campaignService.getCampaign(campaignId);
    }

    @PatchMapping("/{campaignId}")
    public ResponseDto updateCampaign(
            @RequestAttribute("user") User user,
            @PathVariable Long campaignId,
            @RequestBody NewCampaignDto campaignDto
    ) {
        return this.campaignService.updateCampaign(campaignId, campaignDto);
    }


    @DeleteMapping("/{campaignId}")
    public void deleteCampaign(@RequestAttribute("user") User user, @PathVariable Long campaignId) {
        this.campaignService.deleteCampaign(user, campaignId);
    }
}
