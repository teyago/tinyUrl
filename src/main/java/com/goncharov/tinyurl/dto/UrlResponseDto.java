package com.goncharov.tinyurl.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UrlResponseDto {
    private int counter;
    private String url;
    private String alias;
    private String info;
    private LocalDateTime expirationDate;
}