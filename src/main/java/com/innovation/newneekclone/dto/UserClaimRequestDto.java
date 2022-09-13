package com.innovation.newneekclone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserClaimRequestDto {
    private String title;
    private String content;
    private Date date;
}
