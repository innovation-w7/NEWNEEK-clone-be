package com.innovation.newneekclone.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

}

