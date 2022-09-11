package com.innovation.newneekclone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MailDto {
    private String toAddress;
    private String title;
    private String message;
}
