package com.example.tinyurl.repository;

import com.example.tinyurl.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
     Url findByOriginUrl(String url);
}
