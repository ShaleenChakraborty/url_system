package com.urlshortener.shaleen.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class AnalyticsResponse {
    private String originalUrl;
    private String shortCode;
    private String shortUrl;
    @Schema(
            description = "Number of times the shortened URL has been accessed.",
            example = "17"
    )
    private Long clickCount;

    public AnalyticsResponse()
    {

    }

    public AnalyticsResponse(String originalUrl,
                             String shortCode,
                             String shortUrl,
                             Long clickCount) {

        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.shortUrl = shortUrl;
        this.clickCount = clickCount;
    }

    public String getOriginalUrl()
    {
        return originalUrl;
    }
    public void setOriginalUrl(String originalUrl)
    {
        this.originalUrl=originalUrl;
    }

    public String getShortCode()
    {
        return shortCode;
    }
    public void setShortCode(String shortCode)
    {
        this.shortCode=shortCode;
    }

    public String getShortUrl()
    {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl)
    {
        this.shortUrl=shortUrl;
    }

    public Long getClickCount()
    {
        return clickCount;
    }

    public void setClickCount(Long clickCount)
    {
        this.clickCount=clickCount;
    }

}
