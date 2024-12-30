package com.vou.backend.campaign.exception;

public class CampaignAlreadyAddedException extends Exception{
    public CampaignAlreadyAddedException(String message)
    {
        super(message);
    }
}
