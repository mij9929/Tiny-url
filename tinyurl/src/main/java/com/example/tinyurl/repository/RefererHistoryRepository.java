package com.example.tinyurl.repository;

import com.example.tinyurl.domain.RefererHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefererHistoryRepository extends JpaRepository<RefererHistory, Long> {
}
