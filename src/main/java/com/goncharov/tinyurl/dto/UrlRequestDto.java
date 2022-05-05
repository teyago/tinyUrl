package com.goncharov.tinyurl.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UrlRequestDto {
    private int counter;
    private String url;
    private String alias;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;
}