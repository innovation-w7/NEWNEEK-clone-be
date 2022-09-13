package com.innovation.newneekclone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

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

    @Lob //  가변 길이를 갖는 큰 데이터를 저장하는데 사용
    private String contentSum;

    @Lob
    private String content;

    private String date;

    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likeCnt;


}
