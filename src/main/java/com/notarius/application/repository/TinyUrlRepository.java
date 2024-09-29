package com.notarius.application.repository;

import com.notarius.application.model.entity.TinyUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TinyUrlRepository extends JpaRepository<TinyUrl, Long> {

    Optional<TinyUrl> findBySourceUrl(String normalizedUrl);

    Optional<TinyUrl> findByGeneratedUrl(String url);
}
