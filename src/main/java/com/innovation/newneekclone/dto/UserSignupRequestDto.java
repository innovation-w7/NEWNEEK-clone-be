package com.innovation.newneekclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequestDto {
    @NotBlank
    @Pattern(regexp = "[A-Za-z0-9+_.-]+@(.+)$")
    private String email;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

    @NotBlank
    @Size(min = 8, max = 20)
    private String passwordConfirm;

    @NotBlank
    private String nickname;

//    @NotNull
    @JsonProperty("isSubscribe")
    private Boolean isSubscribe;

    private Boolean isAdmin=false;

}
