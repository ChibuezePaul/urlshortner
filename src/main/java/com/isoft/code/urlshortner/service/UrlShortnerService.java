package com.isoft.code.urlshortner.service;

import com.isoft.code.urlshortner.dto.ShortUrlDto;
import com.isoft.code.urlshortner.dto.ShortenUrlDto;

public interface UrlShortnerService {
    ShortenUrlDto shortenUrl(ShortenUrlDto request);

    ShortenUrlDto retrieveUrl(ShortUrlDto request);
}
