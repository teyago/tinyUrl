package com.goncharov.tinyurl.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UrlInfoDto {

    private int numberOfClicks;
    private String url;
    private String alias;
    private String creationDate;
    private String expirationDate;
}
