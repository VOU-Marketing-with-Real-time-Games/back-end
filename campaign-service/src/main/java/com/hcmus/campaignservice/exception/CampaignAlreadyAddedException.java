package com.hcmus.campaignservice.exception;

public class CampaignAlreadyAddedException extends Exception{
    public CampaignAlreadyAddedException(String message)
    {
        super(message);
    }
}
