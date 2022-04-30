package com.goncharov.tinyurl.service;

import com.goncharov.tinyurl.dto.UrlDto;
import com.goncharov.tinyurl.entity.Url;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface UrlService {
    Url createUrl(UrlDto urlDto);
    Url saveUrl(Url url);
    Url getUrlFromAlias(String alias);
    List<Url> findAllByExpirationDateBefore(LocalDateTime localDateTime);
    void deleteUrl(Url url);
}
