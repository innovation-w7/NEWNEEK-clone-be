package com.innovation.newneekclone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id")
    private Long id;

    private String title;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER) // lazy하니까 claims 조회할 때 서버오류..
    private User user;

    @Lob
    private String content;

    private Date date;

    private Boolean isResponse=false;
}
