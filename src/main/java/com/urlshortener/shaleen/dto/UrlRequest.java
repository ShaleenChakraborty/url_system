package com.urlshortener.shaleen.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
public class UrlRequest {

    @NotBlank(message = "URL cannot be empty")
    @Pattern(
            regexp= "^(https?://).+",
            message= "PLease enter a valid URL starting with http:// or https://"

    )
    @Schema(
            description = "The original URL that needs to be shortened.",
            example = "https://www.google.com"
    )
    private String url;

    public UrlRequest()
    {
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url=url;
    }
}
