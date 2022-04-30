package com.goncharov.tinyurl.controller;

import com.goncharov.tinyurl.dto.UrlDto;
import com.goncharov.tinyurl.dto.UrlErrorDto;
import com.goncharov.tinyurl.dto.UrlResponseDto;
import com.goncharov.tinyurl.entity.Url;
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
            UrlResponseDto response = new UrlResponseDto();
            response.setUrl(url.getUrl());
            response.setExpirationDate(url.getExpirationDate());
            response.setAlias(url.getAlias());
            response.setInfo("Short link " + url.getAlias() + " created successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        UrlErrorDto errorDto = new UrlErrorDto();
        errorDto.setStatus("404");
        errorDto.setError("Oops! Unexpected error, try again later!");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_GATEWAY);
    }

    @GetMapping("/{alias}")
    public ResponseEntity<?> redirectToOriginalUrl
            (@PathVariable String alias, HttpServletResponse response) throws IOException {

        UrlErrorDto errorDto = new UrlErrorDto();

        Url url = urlService.getUrlFromAlias(alias);

        if (url == null) {
            errorDto.setError("Url doesn't exist or has expired");
            errorDto.setStatus("400");

            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }

        response.sendRedirect(url.getUrl());
        return new ResponseEntity<>(HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAlias(@RequestBody UrlResponseDto response) {

        Url url = urlService.getUrlFromAlias(response.getAlias());
        urlService.deleteUrl(url);

        response.setInfo("Url " + response.getAlias() + " successfully removed");

        return new ResponseEntity<>(response.getInfo(), HttpStatus.OK);
    }
}
