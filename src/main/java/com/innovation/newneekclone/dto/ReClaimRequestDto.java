package com.innovation.newneekclone.dto;

import com.innovation.newneekclone.entity.Claim;
import com.innovation.newneekclone.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReClaimRequestDto {

    private String title;

    private String content;

    private Date date;
}
