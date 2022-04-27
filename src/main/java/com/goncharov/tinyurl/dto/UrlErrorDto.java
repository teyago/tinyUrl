package com.goncharov.tinyurl.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UrlErrorDto {
    private String status;
    private String error;
}
