package com.isoft.code.urlshortner.controller;

import com.isoft.code.urlshortner.dto.ShortUrlDto;
import com.isoft.code.urlshortner.dto.ShortenUrlDto;
import com.isoft.code.urlshortner.service.UrlShortnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UrlShortnerController {

    private final UrlShortnerService urlShortnerService;

    @PostMapping("/shorten-url")
    public ResponseEntity<ShortenUrlDto> shortenUrl(@RequestBody ShortenUrlDto urlDto) {
        ShortenUrlDto shortenedUrl = urlShortnerService.shortenUrl(urlDto);
        return ResponseEntity.ok(shortenedUrl);
    }

    @PostMapping("/long-url")
    public ResponseEntity retrieveUrl(@RequestBody ShortUrlDto urlDto) {
        ShortenUrlDto longUrl = urlShortnerService.retrieveUrl(urlDto);
        return ResponseEntity.ok(longUrl);
    }

}
