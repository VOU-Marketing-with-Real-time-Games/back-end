package com.hcmus.campaignservice.service;
import com.hcmus.campaignservice.client.UserClient;
import com.hcmus.campaignservice.dto.UserRespondDto;
import com.hcmus.campaignservice.exception.CampaignAlreadyAddedException;
import com.hcmus.campaignservice.exception.CampaignNotFoundException;
import com.hcmus.campaignservice.model.Campaign;
import com.hcmus.campaignservice.model.FavoriteCampaignUser;
import com.hcmus.campaignservice.repository.CampaignRepository;
import com.hcmus.campaignservice.repository.FavoriteCampaignUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteCampaignService {
    private final FavoriteCampaignUserRepository favoriteCampaignUserRepository;
    private final UserClient userClient;
    private final CampaignRepository campaignRepository;
    private final JavaMailSender mailSender;
    /**
     * Add a campaign to favorites
     * @param userId
     * @param campaignId
     * @return
     * @throws CampaignNotFoundException
     * @throws CampaignAlreadyAddedException
     */
    public FavoriteCampaignUser addFavoriteCampaign(Long userId, Long campaignId) throws CampaignNotFoundException, CampaignAlreadyAddedException {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new CampaignNotFoundException("Campaign not found"));
        UserRespondDto userRespondDto = userClient.getUserByID(userId);
        if(favoriteCampaignUserRepository.findByUserIdAndCampaignId(userId,campaignId).isPresent()){
            throw new CampaignAlreadyAddedException("Campaign already added to favorites");
        }
        FavoriteCampaignUser favorite = new FavoriteCampaignUser();
        favorite.setUserId(userId);
        favorite.setCampaign(campaign);
        favorite.setAddToTime(new Date());
        return favoriteCampaignUserRepository.save(favorite);
    }

    /**
     * Scheduled task that runs every day at 10 AM to send reminder emails
     * to users who have added a campaign to their favorites.
     * The email is sent 2 days before the campaign's start date.
     *
     * @return void This method does not return any value.
     */
    @Scheduled(cron = "0 0 10 * * ?") // Runs every day at 10 AM
    public void sendCampaignReminderEmails() {
        Date twoDaysLater = getDateAfterDays(2);
        List<FavoriteCampaignUser> favoriteCampaigns = favoriteCampaignUserRepository.findByCampaign_StartDate(twoDaysLater);
        for (FavoriteCampaignUser fav : favoriteCampaigns) {
            UserRespondDto user = userClient.getUserByID(fav.getUserId());
            if (user != null) {
                sendEmail(user.getEmail(), fav.getCampaign().getName(), fav.getCampaign().getStartDate());
            }
        }
    }
    private void sendEmail(String to, String campaignName, Date startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = sdf.format(startDate);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Upcoming Campaign Reminder!");
        message.setText("Hello,\n\nYou have favorited the campaign '" + campaignName + "'.\n" +
                "It will start on " + formattedDate + ".\n\nDon't miss out!");
        mailSender.send(message);
        System.out.println("Reminder email sent to: " + to);
    }
    private Date getDateAfterDays(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }
}