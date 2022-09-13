package com.innovation.newneekclone.service;

import com.innovation.newneekclone.dto.ClaimResponseDto;
import com.innovation.newneekclone.dto.ReClaimRequestDto;
import com.innovation.newneekclone.dto.ReClaimResponseDto;
import com.innovation.newneekclone.dto.ResponseDto;
import com.innovation.newneekclone.entity.Claim;
import com.innovation.newneekclone.entity.ReClaim;
import com.innovation.newneekclone.entity.User;
import com.innovation.newneekclone.repository.ClaimRepository;
import com.innovation.newneekclone.repository.ReClaimRepository;
import com.innovation.newneekclone.repository.UserRepository;
import com.innovation.newneekclone.security.UserDetailsImpl;
import com.innovation.newneekclone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final ClaimRepository claimRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ReClaimRepository reClaimRepository;

    public ResponseDto<?> getUsers() {
        List<User> users= userRepository.findAll();
        return ResponseDto.success(users);
    }
    public ResponseDto<?> getClaims() {
        List<Claim> claims = claimRepository.findAll();
        List<ClaimResponseDto> claimResponseDto = new ArrayList<>();
        for(Claim claim:claims) {
            claimResponseDto.add(ClaimResponseDto.builder()
                    .id(claim.getId())
                            .title(claim.getTitle())
                            .content(claim.getContent())
                            .userEmail(claim.getUser().getEmail())
                            .date(claim.getDate())
                    .build());
        }
        return ResponseDto.success(claimResponseDto);
    }
//    public ResponseDto<?> reClaim(Long claimId, ReClaimRequestDto requestDto, HttpServletRequest request) {
//        Optional<Claim> claim = claimRepository.findById(claimId);
//        if(claim.isEmpty()){
//            return ResponseDto.fail("WRONG_DIRECTION","답변할 게시글이 없습니다.");
//        }
//        if(!reClaimRepository.findByClaim(claim.get()).isEmpty()){
//            return ResponseDto.fail("WRONG_REQUEST","이미 답변이 등록된 게시글입니다.");
//        }
//        Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenProvider.resolveToken(request));
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//        ReClaim reClaim = ReClaim.builder()
//                .claim(claim.get())
//                .user(userDetails.getUser())
//                .date(new Date())
//                .title(requestDto.getTitle())
//                .content(requestDto.getContent())
//                .build();
//        reClaimRepository.save(reClaim);
//
//        ReClaimResponseDto reClaimResponseDto = ReClaimResponseDto.builder()
//                .id(reClaim.getId())
//                .claimId(claim.get().getId())
//                .userEmail(userDetails.getUser().getEmail())
//                .content(requestDto.getContent())
//                .title(requestDto.getTitle())
//                .date(requestDto.getDate())
//                .build();
//       // claim.get().getIsResponse() 답변이 달리면 true로 변경하고 저장하고 싶은데.......
//        return ResponseDto.success(reClaimResponseDto);
//    }
}
