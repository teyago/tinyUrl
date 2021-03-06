package com.goncharov.tinyurl.repository;

import com.goncharov.tinyurl.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Url findByAlias(String alias);

    boolean existsByAlias(String alias);

    void deleteUrlByAlias(String alias);

    void deleteAllByExpirationDateBefore(LocalDateTime time);

}
