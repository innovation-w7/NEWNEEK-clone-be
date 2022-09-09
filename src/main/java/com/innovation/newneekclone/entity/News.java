package com.innovation.newneekclone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder // 아래 생성자 둘 다 있어야 에러 안남
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;

    private String category;

    private String title;

    @Lob
    private String contentSum;

    @Lob
    private String content;

    private String date;

    private Long likeCnt;
}
