package com.example.tinyurl.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;

@Getter
@Entity
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_url", nullable = false)
    private String originUrl;

    @Column(name="visit_count")
    private Long visitCount = 0L;

    protected  Url(){

    }
    private Url(String originUrl) {
        this.originUrl = originUrl;
    }

    public static Url of(String originUrl) {
        return new Url(originUrl);
    }

    public void increaseVisitCount(){
        this.visitCount ++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Url url)) return false;
        return Objects.equals(id, url.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
