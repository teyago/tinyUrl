package com.goncharov.tinyurl.repository;

import com.goncharov.tinyurl.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Url findByAlias(String alias);

    List<Url> findAllByExpirationDateBefore(LocalDateTime time);
}
