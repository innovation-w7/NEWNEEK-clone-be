package com.innovation.newneekclone;

import com.innovation.newneekclone.entity.Like;
import com.innovation.newneekclone.entity.News;
import com.innovation.newneekclone.entity.User;
import com.innovation.newneekclone.repository.LikeRepository;
import com.innovation.newneekclone.repository.NewsRepository;
import com.innovation.newneekclone.repository.NewsRepositorySupport;
import com.innovation.newneekclone.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.aspectj.lang.annotation.After;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@SpringBootTest
class NewneekCloneApplicationTests {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsRepositorySupport newsRepositorySupport;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    JPAQueryFactory jpaQueryFactory;
    @Test
    public void NewsData(){
        String Url = "https://newneek.co/";
        Connection conn = Jsoup.connect(Url);
        //titles = #root > section > div > a:nth-child(1) > div > div > h3
        //contents = #root > section > div > a:nth-child(1) > div > div > p
        //categories = #root > section > div > a:nth-child(1) > div > div > i
        //date = #root > section > div > a:nth-child(1) > div > div > time

        try {
            Document document = conn.get();
            Elements titles = document.select("#root  section  div  a");
            Elements contents = document.select("#root  section  div  a");
            Elements categories = document.select("#root > section > div > a");
            Elements dates = document.select("#root > section > div > a");

            for(int i =0; i < 10; i++) {
                String title = titles.select("div div h3").get(i).text();
                String content = contents.select("div div p").get(i).text();
                String category = categories.select("div div i").get(i).text();
                String data = dates.select("div div time").get(i).text();
                String content_sum = content.substring(0, 60) + "...";
                News news = new News(Long.valueOf(i), category, title, content_sum, content, data, 0);
                newsRepository.save(news);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void userDelete(){
        for(int i=1;i<10;i++){
            User user = User.builder()
                    .id(Long.valueOf(i))
                    .isSubscribe(false)
                    .email("email0"+i+"@email.email")
                    .nickname("nick")
                    .password(passwordEncoder.encode("12345678"))
                    .role("ROLE_USER")
                    .build();
            userRepository.save(user);
        }
    }


    @Test
    public void querydsl_검색(){
        //given
        String keyword = "뉴니커";
        //when
        List<News> result = newsRepositorySupport.findByKeyword(keyword);
        //then
        assertThat(result.size()).isEqualTo(8);
    }

    @Test
    void contextLoads() {
    }



}
