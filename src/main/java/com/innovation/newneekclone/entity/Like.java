package com.innovation.newneekclone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "news_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private News news;
}
