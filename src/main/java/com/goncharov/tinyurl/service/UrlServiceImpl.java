package com.goncharov.tinyurl.service;

import com.goncharov.tinyurl.dto.UrlDto;
import com.goncharov.tinyurl.entity.Url;
import com.goncharov.tinyurl.repository.UrlRepository;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class UrlServiceImpl implements UrlService {
    private final UrlRepository urlRepository;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public Url createUrl(UrlDto urlDto) {
        if (StringUtils.isNotEmpty(urlDto.getUrl())) {
            Url url = new Url();
            url.setCreationDate(LocalDateTime.now());
            url.setUrl(urlDto.getUrl());
            url.setAlias(encodeUrl(urlDto.getUrl()));
            url.setExpirationDate(getExpirationDate(urlDto.getExpirationDate(), url.getCreationDate()));

            return saveUrl(url);
        }
        return null;
    }

    private LocalDateTime getExpirationDate(String expirationDate, LocalDateTime creationDate) {
        if (StringUtils.isBlank(expirationDate)) {
            return creationDate.plusMinutes(10);
        }
        return LocalDateTime.parse(expirationDate);
    }

    private String encodeUrl(String url) {
        LocalDateTime time = LocalDateTime.now();
        return Hashing.murmur3_32_fixed()
                .hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();
    }

    @Override
    public Url saveUrl(Url url) {
        return urlRepository.save(url);
    }

    @Override
    public Url getUrlFromAlias(String alias) {
        return urlRepository.findByAlias(alias);
    }

    @Override
    public void deleteUrl(Url url) {
        urlRepository.delete(url);
    }
}
