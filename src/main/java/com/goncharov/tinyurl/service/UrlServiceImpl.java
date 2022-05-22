package com.goncharov.tinyurl.service;

import com.goncharov.tinyurl.dto.UrlCreateDto;
import com.goncharov.tinyurl.dto.UrlGenerateDto;
import com.goncharov.tinyurl.entity.Url;
import com.goncharov.tinyurl.exception.UrlDoesntExistException;
import com.goncharov.tinyurl.exception.UrlIsNullException;
import com.goncharov.tinyurl.exception.UrlIsToShortException;
import com.goncharov.tinyurl.repository.UrlRepository;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class UrlServiceImpl implements UrlService {
    private final UrlRepository urlRepository;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public Url createUrl(UrlCreateDto urlCreateDto) {
        String urlStr = urlCreateDto.getUrl();

        if (StringUtils.isEmpty(urlStr)) {
            throw new UrlIsNullException();
        }
        if (urlStr.length() < 5) {
            throw new UrlIsToShortException();
        }
        Url url = new Url();

        url.setCreationDate(LocalDateTime.now());
        url.setUrl(urlStr);
        url.setAlias(encodeUrl(urlStr));
        url.setCounter(0);
        url.setExpirationDate(setDate(urlCreateDto.getExpirationDate(), url.getCreationDate()));

        saveUrl(url);

        return url;

    }

    private LocalDateTime setDate(String expirationDate, LocalDateTime creationDate) {
        if (StringUtils.isBlank(expirationDate)) {
            return creationDate.plusMinutes(10);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(expirationDate, formatter);
    }

    private String encodeUrl(String url) {
        LocalDateTime time = LocalDateTime.now();
        return Hashing.murmur3_32_fixed()
                .hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();
    }

    @Modifying
    public void incrementCounter(Url url) {
        url.setCounter(url.getCounter() + 1);
        urlRepository.save(url);
    }

    @Override
    public void saveUrl(Url url) {
        urlRepository.save(url);
    }

    @Override
    public Url getUrlFromAlias(String alias) {

        if (!urlRepository.existsByAlias(alias)) {
            throw new UrlDoesntExistException();
        }

        return urlRepository.findByAlias(alias);
    }

    @Override
    public void sendRedirect(String alias, HttpServletResponse response) throws IOException {

        Url url = urlRepository.findByAlias(alias);

        if (url == null) {
            throw new UrlDoesntExistException();
        }

        incrementCounter(url);
        response.sendRedirect(url.getUrl());
    }

    @Transactional
    @Override
    public void deleteAllByExpirationDateBefore(LocalDateTime localDateTime) {
        urlRepository.deleteAllByExpirationDateBefore(localDateTime);
    }

    @Transactional
    @Override
    public void deleteUrlByAlias(UrlGenerateDto requestDto) {

        String alias = requestDto.getAlias();

        if (StringUtils.isEmpty(alias)) {
            throw new UrlIsNullException();
        }

        if (!urlRepository.existsByAlias(alias)) {
            throw new UrlDoesntExistException();
        }

        urlRepository.deleteUrlByAlias(alias);
    }
}
