package com.urlshortener.shaleen.service;

import com.urlshortener.shaleen.dto.AnalyticsResponse;
import com.urlshortener.shaleen.dto.UrlResponse;
import com.urlshortener.shaleen.entity.Url;
import com.urlshortener.shaleen.exception.ShortUrlNotFoundException;
import com.urlshortener.shaleen.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {

    private static final Logger logger = LoggerFactory.getLogger(UrlService.class);

    // Characters used for generating Base62 Short URLs
    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int SHORT_CODE_LENGTH = 6;

    private final Random random = new Random();

    private final UrlRepository urlRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public UrlService(UrlRepository urlRepository,
                      RedisTemplate<String, Object> redisTemplate) {

        this.urlRepository = urlRepository;
        this.redisTemplate = redisTemplate;
    }

    /**
     * Creates a new Short URL
     */
    public UrlResponse createShortUrl(String originalUrl) {

        logger.info("Received request to shorten URL: {}", originalUrl);

        // Duplicate URL Detection
        Optional<Url> existingUrl =
                urlRepository.findByOriginalUrl(originalUrl);

        if (existingUrl.isPresent()) {

            logger.warn(
                    "Duplicate URL detected. Returning existing short URL."
            );

            return mapToResponse(existingUrl.get());
        }

        String shortCode;

        do {
            shortCode = generateShortCode();
        }
        while (urlRepository.existsByShortCode(shortCode));

        Url url = new Url(originalUrl, shortCode);

        Url savedUrl = urlRepository.save(url);

        logger.info(
                "Short URL successfully created with short code {}",
                shortCode
        );

        return mapToResponse(savedUrl);
    }

    /**
     * Redirect Logic with Redis Cache
     */
    public Url getUrlAndIncrementClicks(String shortCode) {

        logger.info(
                "Redirect requested for short code {}",
                shortCode
        );

        // STEP 1 : Check Redis First
        Url url = getUrlFromCache(shortCode);

        if (url != null) {

            logger.info(
                    "Cache HIT for short code {}",
                    shortCode
            );

        } else {

            logger.info(
                    "Cache MISS for short code {}",
                    shortCode
            );

            // Fetch from PostgreSQL
            url = getUrlFromDatabase(shortCode);

            // Store into Redis
            saveUrlToCache(url);

            logger.info(
                    "URL cached successfully."
            );
        }

        // Increment Click Count
        url.setClickCount(url.getClickCount() + 1);

        logger.info(
                "Click count updated to {}",
                url.getClickCount()
        );

        // Save updated object into PostgreSQL
        Url updatedUrl = urlRepository.save(url);

        // Synchronize Redis Cache
        saveUrlToCache(updatedUrl);

        logger.info(
                "Redis cache synchronized successfully."
        );

        return updatedUrl;
    }

    /**
     * Analytics API
     */
    public AnalyticsResponse getAnalytics(String shortCode) {

        Url url = getUrlFromDatabase(shortCode);

        return new AnalyticsResponse(

                url.getOriginalUrl(),
                url.getShortCode(),
                "http://localhost:8080/" + url.getShortCode(),
                url.getClickCount()

        );
    }

    /**
     * Generates Random Base62 Short Code
     */
    private String generateShortCode() {

        StringBuilder shortCode = new StringBuilder();

        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {

            int index = random.nextInt(CHARACTERS.length());

            shortCode.append(
                    CHARACTERS.charAt(index)
            );
        }

        logger.debug(
                "Generated short code {}",
                shortCode
        );

        return shortCode.toString();
    }

    /**
     * Reads URL from Redis Cache
     */
    private Url getUrlFromCache(String shortCode) {

        return (Url) redisTemplate
                .opsForValue()
                .get(shortCode);
    }

    /**
     * Reads URL from PostgreSQL
     */
    private Url getUrlFromDatabase(String shortCode) {

        return urlRepository
                .findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ShortUrlNotFoundException(shortCode));
    }

    /**
     * Stores URL into Redis Cache
     */
    private void saveUrlToCache(Url url)
    {
        logger.info(
                "Updating Redis cache for short code {}",
                url.getShortCode()
        );

        redisTemplate
                .opsForValue()
                .set(
                        url.getShortCode(),
                        url,
                        Duration.ofMinutes(10)
                );
    }

    /**
     * Converts Entity to DTO
     */
    private UrlResponse mapToResponse(Url url) {

        String shortUrl =
                "http://localhost:8080/" + url.getShortCode();

        return new UrlResponse(

                url.getOriginalUrl(),
                shortUrl

        );
    }
}