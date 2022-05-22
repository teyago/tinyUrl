package com.goncharov.tinyurl.controller;

import com.goncharov.tinyurl.dto.UrlCreateDto;
import com.goncharov.tinyurl.dto.UrlGenerateDto;
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

    @PostMapping(value = "/generate")
    public ResponseEntity<?> generateShortLink
            (@RequestBody(required = false) UrlCreateDto urlCreateDto) {

        Url url = urlService.createUrl(urlCreateDto);

        return new ResponseEntity<>(UrlMapper.INSTANCE.generateDto(url), HttpStatus.OK);
    }

    @GetMapping(value = "/{alias}")
    public ResponseEntity<?> redirectToOriginalUrl
            (@PathVariable String alias, HttpServletResponse response) throws IOException {

        urlService.sendRedirect(alias, response);

        return new ResponseEntity<>(HttpStatus.MOVED_PERMANENTLY);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAlias
            (@RequestBody(required = false) UrlGenerateDto requestDto) {

        urlService.deleteUrlByAlias(requestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{alias}/info")
    public ResponseEntity<?> getInfo(@PathVariable String alias) {

        Url url = urlService.getUrlFromAlias(alias);

        return new ResponseEntity<>(UrlMapper.INSTANCE.infoDto(url), HttpStatus.OK);
    }
}
