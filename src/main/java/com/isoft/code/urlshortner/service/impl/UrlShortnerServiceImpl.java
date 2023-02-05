package com.isoft.code.urlshortner.service.impl;

import com.isoft.code.urlshortner.UrlUtil;
import com.isoft.code.urlshortner.dto.ShortUrlDto;
import com.isoft.code.urlshortner.dto.ShortenUrlDto;
import com.isoft.code.urlshortner.entity.UserUrl;
import com.isoft.code.urlshortner.exception.CustomException;
import com.isoft.code.urlshortner.repository.UrlShortnerRepository;
import com.isoft.code.urlshortner.service.UrlShortnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class UrlShortnerServiceImpl implements UrlShortnerService {

    private final UrlShortnerRepository urlShortnerRepository;
    private final UrlUtil urlUtil;
    private static final String INVALID_URL = "Invalid Url";

    @Override
    public ShortenUrlDto shortenUrl(ShortenUrlDto request) {
        String longUrl = request.getUrl();
        if (isInvalidUrl(longUrl)){
            throw new CustomException(INVALID_URL, HttpStatus.BAD_REQUEST);
        }

        return urlShortnerRepository.findShortUrl(longUrl)
            .orElseGet(() -> saveNewUrl(longUrl));
    }

    @Override
    public ShortenUrlDto retrieveUrl(ShortUrlDto request) {
        if (isInvalidUrl(request.getShortUrl())){
            throw new CustomException(INVALID_URL, HttpStatus.BAD_REQUEST);
        }
        return urlShortnerRepository.findLongUrl(request.getShortUrl())
                .orElseThrow(() -> new CustomException("Url not found", HttpStatus.NOT_FOUND));
    }

    private static boolean isInvalidUrl(String url) {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return true;
        }
        return false;
    }

    private ShortenUrlDto saveNewUrl(String longUrl) {
        String shortUrl = urlUtil.generateUrl();
        UserUrl userUrl = new UserUrl();
        userUrl.setLongUrl(longUrl);
        userUrl.setShortUrl(shortUrl);
        urlShortnerRepository.save(userUrl);
        return new ShortenUrlDto(shortUrl);
    }
}