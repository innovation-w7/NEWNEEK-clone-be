package com.innovation.newneekclone.controller;

import com.innovation.newneekclone.dto.ReClaimRequestDto;
import com.innovation.newneekclone.dto.ResponseDto;
import com.innovation.newneekclone.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping ("/getUsers")
    public ResponseDto<?> getUsers() {
        return adminService.getUsers();
    }

    @GetMapping ("/getClaims")
    public ResponseDto<?> getClaims() {
        return adminService.getClaims();
    }
//    @PostMapping("/claim/{claimId}")
//    public ResponseDto<?> reClaim(@PathVariable Long claimId, @RequestBody ReClaimRequestDto requestDto, HttpServletRequest request) {
//        return adminService.reClaim(claimId,requestDto,request);
//    }
}
