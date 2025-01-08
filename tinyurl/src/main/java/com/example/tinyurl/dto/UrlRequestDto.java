package com.example.tinyurl.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

@ToString
@Getter
@Setter
public class UrlRequestDto {

    @URL
    private String url;

}
