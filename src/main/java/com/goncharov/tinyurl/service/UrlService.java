package com.goncharov.tinyurl.service;

import com.goncharov.tinyurl.dto.UrlDto;
import com.goncharov.tinyurl.entity.Url;
import org.springframework.stereotype.Service;

@Service
public interface UrlService {
    Url createUrl(UrlDto urlDto);
    Url saveUrl(Url url);
    Url getUrlFromAlias(String alias);
    void deleteUrl(Url url);
}
