package com.innovation.newneekclone.repository;

import com.innovation.newneekclone.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findById(Long newsId);
    List<News> findAllByOrderByDateDesc();
    List<News> findAllByCategoryOrderByDateDesc(String category);

}
