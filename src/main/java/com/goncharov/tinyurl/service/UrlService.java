package com.goncharov.tinyurl.service;

import com.goncharov.tinyurl.dto.UrlCreateDto;
import com.goncharov.tinyurl.dto.UrlGenerateDto;
import com.goncharov.tinyurl.entity.Url;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public interface UrlService {
    Url getUrlFromAlias(String alias);

    Url createUrl(UrlCreateDto urlCreateDto);

    void saveUrl(Url url);

    void sendRedirect(String alias, HttpServletResponse response) throws IOException;

    void deleteUrlByAlias(UrlGenerateDto requestDto);

    void deleteAllByExpirationDateBefore(LocalDateTime localDateTime);
}
