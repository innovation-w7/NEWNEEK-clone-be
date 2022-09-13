package com.innovation.newneekclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimResponseDto {
    private Long id;

    private String title;
    @Lob
    private String content;

    private Date date;

    private String userEmail;
}
