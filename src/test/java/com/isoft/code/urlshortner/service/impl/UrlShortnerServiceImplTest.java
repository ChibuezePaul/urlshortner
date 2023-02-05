package com.isoft.code.urlshortner.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.isoft.code.urlshortner.UrlUtil;
import com.isoft.code.urlshortner.dto.ShortUrlDto;
import com.isoft.code.urlshortner.dto.ShortenUrlDto;
import com.isoft.code.urlshortner.entity.UserUrl;
import com.isoft.code.urlshortner.exception.CustomException;
import com.isoft.code.urlshortner.repository.UrlShortnerRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UrlShortnerServiceImplTest {

  @Autowired
  private UrlShortnerServiceImpl underTest;

  @MockBean
  private UrlShortnerRepository urlShortnerRepository;

  @MockBean
  private UrlUtil urlUtil;

  private final String shortUrl = "http://localhost:8080/short-url";
  private final String longUrl = "http://localhost:8080/long-url";

  @Test
  void shortenUrl_shouldThrowExceptionWhenUrlIsInvalid() {
    assertThrows(CustomException.class, () -> underTest.shortenUrl(new ShortenUrlDto()));
  }

  @Test
  void shortenUrl_shouldReturnShortUrlWhenLongUrlExistInDatabase() {
    ShortenUrlDto dto = new ShortenUrlDto(longUrl);

    when(urlShortnerRepository.findShortUrl(longUrl))
        .thenReturn(Optional.of(new ShortenUrlDto(shortUrl)));

    ShortenUrlDto shortenUrl = underTest.shortenUrl(dto);

    assertEquals(shortUrl, shortenUrl.getUrl());
    verify(urlShortnerRepository, atLeastOnce()).findShortUrl(longUrl);
    verifyNoMoreInteractions(urlShortnerRepository);
  }

  @Test
  void shortenUrl_shouldReturnShortUrlAndSaveLongUrlToDatabaseWhenLongUrlDoesNotExistInDatabase() {
    ShortenUrlDto dto = new ShortenUrlDto(longUrl);

    when(urlUtil.generateUrl())
        .thenReturn(shortUrl);

    ShortenUrlDto shortenUrl = underTest.shortenUrl(dto);

    assertEquals(shortUrl, shortenUrl.getUrl());
    verify(urlShortnerRepository, atLeastOnce()).findShortUrl(longUrl);
    verify(urlUtil, atLeastOnce()).generateUrl();
    verify(urlShortnerRepository, atLeastOnce()).save(any(UserUrl.class));
  }

  @Test
  void retrieveUrl_shouldThrowExceptionWhenUrlIsInvalid() {
    assertThrows(CustomException.class, () -> underTest.retrieveUrl(new ShortUrlDto()));
  }

  @Test
  void retrieveUrl_shouldReturnLongUrlWhenShortUrlExistsInDatabase() {
    when(urlShortnerRepository.findLongUrl(shortUrl))
        .thenReturn(Optional.of(new ShortenUrlDto(longUrl)));

    ShortenUrlDto shortenUrlDto = underTest.retrieveUrl(new ShortUrlDto(shortUrl));

    assertEquals(longUrl, shortenUrlDto.getUrl());
    verify(urlShortnerRepository, atLeastOnce()).findLongUrl(shortUrl);
  }

  @Test
  void retrieveUrl_shouldThrowExceptionWhenShortUrlDoesNotExistsInDatabase() {
    assertThrows(CustomException.class, () -> underTest.retrieveUrl(new ShortUrlDto(shortUrl)));
    verify(urlShortnerRepository, atLeastOnce()).findLongUrl(shortUrl);
  }
}