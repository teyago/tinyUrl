package com.goncharov.tinyurl.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UrlDto {
    private String url;
    private String expirationDate;
}
