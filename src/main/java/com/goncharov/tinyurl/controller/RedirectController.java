package com.goncharov.tinyurl.controller;

import com.goncharov.tinyurl.dto.UrlDto;
import com.goncharov.tinyurl.dto.UrlRequestDto;
import com.goncharov.tinyurl.entity.Url;
import com.goncharov.tinyurl.exception.ApiRequestException;
import com.goncharov.tinyurl.mapper.UrlMapper;
import com.goncharov.tinyurl.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/tinyurl")
public class RedirectController {
    private final UrlService urlService;

    @Autowired
    public RedirectController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping(value = "/generate")
    public ResponseEntity<?> generateShortLink(@RequestBody(required = false) UrlDto urlDto) throws ApiRequestException {
        if (urlDto == null) {
            throw new ApiRequestException("Url must be not null.");
        }

        if (urlDto.getUrl().length() < 4) {
            throw new ApiRequestException("Oops! Url is to short.");
        }

        Url url = urlService.createUrl(urlDto);

        return new ResponseEntity<>(UrlMapper.INSTANCE.toDto(url), HttpStatus.OK);
    }

    @GetMapping(value = "/{alias}")
    public ResponseEntity<?> redirectToOriginalUrl
            (@PathVariable String alias, HttpServletResponse response) throws IOException, ApiRequestException {
        Url url = urlService.getUrlFromAlias(alias);

        if (url == null) {
            throw new ApiRequestException("Url is outdated or was never created.");
        }

        response.sendRedirect(url.getUrl());
        urlService.incrementCounter(url);

        return new ResponseEntity<>(HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAlias(@RequestBody(required = false) UrlRequestDto requestDto) throws ApiRequestException {
        if (requestDto == null) {
            throw new ApiRequestException("Url must be not null.");
        }

        Url url = urlService.getUrlFromAlias(requestDto.getAlias());

        if (url == null) {
            throw new ApiRequestException("Url is outdated or was never created.");
        }

        urlService.deleteUrl(url);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/info/{alias}")
    public ResponseEntity<?> getInfo(@PathVariable String alias) throws ApiRequestException {
        Url url = urlService.getUrlFromAlias(alias);

        if (url == null) {
            throw new ApiRequestException("Url is outdated or was never created.");
        }

        return new ResponseEntity<>(UrlMapper.INSTANCE.toDto(url), HttpStatus.OK);
    }
}
