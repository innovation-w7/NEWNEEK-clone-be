package com.innovation.newneekclone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReClaim_id")
    private Long id;

    private String title;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @JoinColumn(name = "Claimid", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Claim claim;

    @Lob
    private String content;

    private Date date;
}
