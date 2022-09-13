package com.innovation.newneekclone;

import com.innovation.newneekclone.entity.Like;
import com.innovation.newneekclone.entity.News;
import com.innovation.newneekclone.entity.QLike;
import com.innovation.newneekclone.entity.User;
import com.innovation.newneekclone.repository.LikeRepository;
import com.innovation.newneekclone.repository.NewsRepository;
import com.innovation.newneekclone.repository.UserRepository;
import com.innovation.newneekclone.security.Authority;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

import static com.innovation.newneekclone.entity.QLike.*;
import static com.innovation.newneekclone.entity.QNews.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
//@Transactional
public class QueryDslTest {
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Test
    public void setNewsData(){
        for(int i=0;i<10;i++){
            News news = News.builder()
                    .id(Long.valueOf(i))
                    .title("title"+i)
                    .content("content"+i)
                    .category("인권")
                    .date(String.valueOf(i))
                    .contentSum("contentSum"+i)
                    .likeCnt(null)
                    .build();
            newsRepository.save(news);
        }
    }

    @Test
    public void setUserData(){
        for(int i=0;i<5;i++){
            User user = User.builder()
                    .id(Long.valueOf(i))
                    .email("user1"+i+"@naver.com")
                    .nickname("nick0"+i)
                    .password(passwordEncoder.encode("12345678"))
                    .isSubscribe(true)
                    .role(Authority.ROLE_USER)
                    .build();
            userRepository.save(user);
        }
        for(int i=5;i<10;i++){
            User user = User.builder()
                    .id(Long.valueOf(i))
                    .email("admin0"+i+"@naver.com")
                    .nickname("nick0"+i)
                    .password(passwordEncoder.encode("ADMIN1234"))
                    .isSubscribe(true)
                    .role(Authority.ROLE_ADMIN)
                    .build();
            userRepository.save(user);
        }
    }

    @Test
    public void setLikeDate(){
        //for(int i=0;i<3;i++) {
            News news = newsRepository.findById(Long.valueOf(4)).get();
            User user = userRepository.findById(Long.valueOf(4)).get();
            Like like = Like.builder()
                    .id(Long.valueOf(4))
                    .user(user)
                    .news(news)
                    .build();
            likeRepository.save(like);
        //}
    }

    @Test
    public void 기본쿼리(){
        List<News> results = jpaQueryFactory
                .selectFrom(news)
                .fetch();

        assertThat(results.size()).isEqualTo(12);
    }

    @Test
    public void 기본쿼리_조건절1(){
        List<News> results = jpaQueryFactory
                .selectFrom(news)
                .where(news.title.contains("title1"))
                .fetch();
        assertThat(results.size()).isEqualTo(1);
    }

    @Test
    public void 기본쿼리_조건절2(){
        List<News> results = jpaQueryFactory
                .selectFrom(news)
                .where(
                        news.title.contains("title"),
                        news.content.startsWith("content1")
                )
                .fetch();
        assertThat(results.size()).isEqualTo(1);
    }

    @Test
    public void 기본쿼리_정렬(){
        List<News> results = jpaQueryFactory
                .selectFrom(news)
                .orderBy(news.date.desc())
                .fetch();
        assertThat(results.size()).isEqualTo(12);
        assertThat(results.get(0).getTitle()).isEqualTo("title9");
    }

    @Test
    public void 기본쿼리_페이징(){
        List<News> results = jpaQueryFactory
                .selectFrom(news)
                .offset(1) // 시작줄
                .limit(3)
                .fetch();

        System.out.println("total"+results.size());
        assertThat(results.size()).isEqualTo(3);
        assertThat(results.get(0).getTitle()).isEqualTo("title2");
    }

    @Test
    public void 기본join(){
        List<News> fetch = jpaQueryFactory
                .selectFrom(news)
                .join(news.likeCnt, like) // inner join
                .fetch();
        fetch.forEach(System.out::println); // 좋아요한 news만 출력됨
    }

//    @Test
//    public void 연관관계없는join(){
//        List<News> fetch = jpaQueryFactory
//                .select(news)
//                .from(news,like)
//                .where(like.id.eq(news.likeCnt.get(1)))
//                .fetch();
//        assertThat(fetch.size()).isEqualTo(2);
//    }


}