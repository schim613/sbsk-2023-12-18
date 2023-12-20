package com.ll.app20231218.domain.member.member.controller;

import com.ll.app20231218.domain.member.member.service.MemberService;
import com.ll.app20231218.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class ApiV1MembersController {
    private final MemberService memberService;

    @AllArgsConstructor
    @Getter
    public static
    class LoginResponseBody {
        @NotBlank
        private String accessToken;
        @NotBlank
        private String refreshToken;
    }

    @Getter
    @Setter
    public static class LoginRequestBody {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @PostMapping("/login")
    public RsData<LoginResponseBody> login(
        @Valid @RequestBody LoginRequestBody body
    ) {
        RsData<MemberService.AuthAndMakeTonkensResponseBody> authAndMakeTonkensRs = memberService.authAndMakeTokens(body.getUsername(), body.getPassword());

        return RsData.of(
                authAndMakeTonkensRs.getResultCode(),
                authAndMakeTonkensRs.getMsg(),
                new LoginResponseBody(
                        authAndMakeTonkensRs.getData().getAccessToken(),
                        authAndMakeTonkensRs.getData().getRefreshToken()
                )
        );
    }
}
