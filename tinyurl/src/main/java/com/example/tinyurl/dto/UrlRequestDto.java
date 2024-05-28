package com.example.tinyurl.dto;

import com.example.tinyurl.domain.Url;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
public class UrlRequestDto {

    @URL
    private String url;

}
