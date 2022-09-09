package com.innovation.newneekclone.repository;

import com.innovation.newneekclone.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
