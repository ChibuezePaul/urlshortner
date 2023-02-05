package com.isoft.code.urlshortner.repository;

import com.isoft.code.urlshortner.dto.ShortenUrlDto;
import com.isoft.code.urlshortner.entity.UserUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlShortnerRepository extends JpaRepository<UserUrl, Long> {

    @Query("select new com.isoft.code.urlshortner.dto.ShortenUrlDto(longUrl) from UserUrl where shortUrl = :shortUrl")
    Optional<ShortenUrlDto> findLongUrl(String shortUrl);

    @Query("select new com.isoft.code.urlshortner.dto.ShortenUrlDto(shortUrl) from UserUrl where longUrl = :longUrl")
    Optional<ShortenUrlDto> findShortUrl(String longUrl);
}
