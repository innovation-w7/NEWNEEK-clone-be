package com.innovation.newneekclone.repository;

import com.innovation.newneekclone.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByOrderByDate(); // 날짜순 전체 조회
    List<News> findAllByCategory(); //카테고리별 조회
}
