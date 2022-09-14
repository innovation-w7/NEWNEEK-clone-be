package com.innovation.newneekclone.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDto {
    private String password;
    private String nickname;
    private Boolean isSubscribe;
}
