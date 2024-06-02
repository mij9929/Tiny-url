package com.example.tinyurl.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RefererHistory extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shorten_url_id")
    @Setter
    private ShortenUrl shortenUrl;

    @Column
    private String referer;

    private RefererHistory(ShortenUrl shortenUrl, String referer) {
        this.shortenUrl = shortenUrl;
        this.referer = referer;
    }

    public static RefererHistory of(ShortenUrl shortenUrl, String referer) {
        return new RefererHistory(shortenUrl, referer);
    }
}
