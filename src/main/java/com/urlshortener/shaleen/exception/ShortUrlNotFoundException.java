package com.urlshortener.shaleen.exception;

public class ShortUrlNotFoundException extends RuntimeException {

    public ShortUrlNotFoundException(String shortcode)
    {
        super("Short URL '" + shortcode + "'not found.");
    }
}
