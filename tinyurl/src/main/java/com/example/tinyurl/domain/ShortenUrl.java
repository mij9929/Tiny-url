package com.example.tinyurl.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
public class ShortenUrl extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_url", nullable = false)
    private String originUrl;

    @Column(name="hit")
    private Long hit = 0L;

//    @OneToMany(mappedBy = "shortenUrl", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<RefererHistory> refererHistories;

    protected ShortenUrl(){

    }

    private ShortenUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public static ShortenUrl of(String originUrl) {
        return new ShortenUrl(originUrl);
    }

    public void increaseHit(){
        this.hit ++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShortenUrl url)) return false;
        return Objects.equals(id, url.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
