package com.innovation.newneekclone.repository;

//import com.innovation.newneekclone.entity.News;
import com.innovation.newneekclone.entity.News;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.innovation.newneekclone.entity.QNews.news;

@Repository
public class NewsRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public NewsRepositorySupport(JPAQueryFactory jpaQueryFactory){
        super(News.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<News> findByKeyword(String keyword){
        return jpaQueryFactory.selectFrom(news)
                .where(news.title.contains(keyword).or(news.content.contains(keyword)))
                .fetch();
    }
}
