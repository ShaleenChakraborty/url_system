package com.urlshortener.shaleen.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UrlResponse {
    @Schema(
            description = "The original URL.",
            example = "https://www.google.com"
    )
    private String originalUrl;

    @Schema(
            description = "Generated shortened URL.",
            example = "http://localhost:8080/Ab12Cd"
    )
    private String shortUrl;
    public UrlResponse()
    {}

    public UrlResponse(String originalUrl, String shortUrl)
    {
        this.originalUrl=originalUrl;
        this.shortUrl=shortUrl;
    }

    public String getOriginalUrl()
    {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl)
    {
        this.originalUrl=originalUrl;
    }

    public String getShortUrl()
    {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl)
    {
        this.shortUrl=shortUrl;
    }
}
