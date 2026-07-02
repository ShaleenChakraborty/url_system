package com.urlshortener.shaleen.controller;
import com.urlshortener.shaleen.dto.AnalyticsResponse;
import com.urlshortener.shaleen.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.urlshortener.shaleen.entity.Url;
import com.urlshortener.shaleen.dto.UrlRequest;
import com.urlshortener.shaleen.dto.UrlResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Tag(
        name = "URL Shortener",
        description = "Operations related to shortening URLs, redirection and analytics."
)
@RestController
public class UrlController {
    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);
    private final UrlService urlService;

    public UrlController(UrlService urlService)
    {
        this.urlService = urlService;
    }


    @Operation(
            summary = "Create a Short URL",
            description = "Accepts a long URL and generates a unique shortened URL."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Short URL created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid URL supplied")
    })
    @PostMapping("/shorten")
    public UrlResponse shortenUrl(@Valid @RequestBody UrlRequest request)
    {
        logger.info("POST /shorten endpoint called");
        UrlResponse response=urlService.createShortUrl(request.getUrl());
        logger.info("Short URL created successfully");
        return response;

    }

    @GetMapping("/{shortCode}")
    public void redirectToOriginalUrl(@PathVariable String shortCode,
                                      HttpServletResponse response) throws IOException
    {
        logger.info(
                "Redirect requested for short code {}",
                shortCode
        );

        Url url = urlService.getUrlAndIncrementClicks(shortCode);

        logger.info(
                "Redirecting to {}",
                url.getOriginalUrl()
        );

        response.sendRedirect(url.getOriginalUrl());

    }

    @GetMapping("/analytics/{shortCode}")
    public AnalyticsResponse getAnalytics(@PathVariable String shortCode)
    {
        logger.info("Analytics requested for short code {}", shortCode);
        AnalyticsResponse response=urlService.getAnalytics(shortCode);

        logger.info("Analytics returned successfully for {}", shortCode);

        return response;
    }


}

