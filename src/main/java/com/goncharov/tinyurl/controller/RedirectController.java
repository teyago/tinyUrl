package com.goncharov.tinyurl.controller;

import com.goncharov.tinyurl.dto.UrlDto;
import com.goncharov.tinyurl.dto.UrlErrorDto;
import com.goncharov.tinyurl.dto.UrlResponseDto;
import com.goncharov.tinyurl.entity.Url;
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

    @PostMapping("/generate")
    public ResponseEntity<?> generateShortLink(@RequestBody UrlDto urlDto) {
        Url url = urlService.createUrl(urlDto);

        if (url != null) {
            return new ResponseEntity<>(UrlMapper.INSTANCE.toDto(url), HttpStatus.OK);
        }

        UrlErrorDto errorDto = new UrlErrorDto();
        errorDto.setStatus("404");
        errorDto.setError("Oops! Unexpected error, try again later!");

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_GATEWAY);
    }

    @GetMapping("/{alias}")
    public ResponseEntity<?> redirectToOriginalUrl
            (@PathVariable String alias, HttpServletResponse response) throws IOException {
        Url url = urlService.getUrlFromAlias(alias);

        if (url == null) {
            UrlErrorDto errorDto = new UrlErrorDto();
            errorDto.setError("Url doesn't exist or has expired");
            errorDto.setStatus("400");

            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }

        urlService.incrementCounter(url);
        response.sendRedirect(url.getUrl());

        return new ResponseEntity<>(HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAlias(@RequestBody UrlResponseDto response) {
        Url url = urlService.getUrlFromAlias(response.getAlias());
        urlService.deleteUrl(url);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/info/{alias}")
    public ResponseEntity<?> getInfo(@PathVariable String alias) {
        Url url = urlService.getUrlFromAlias(alias);

        return new ResponseEntity<>(UrlMapper.INSTANCE.toDto(url), HttpStatus.OK);
    }
}
