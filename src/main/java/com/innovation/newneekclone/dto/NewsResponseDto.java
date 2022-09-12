package com.innovation.newneekclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponseDto {
    private Long id;

    private String category;

    private String title;

    @Lob
    private String contentSum;

    private String date;
}
