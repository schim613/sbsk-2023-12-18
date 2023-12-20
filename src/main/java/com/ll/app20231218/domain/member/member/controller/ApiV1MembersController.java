package com.ll.app20231218.domain.member.member.controller;

import com.ll.app20231218.domain.member.member.entity.Member;
import com.ll.app20231218.domain.member.member.service.MemberService;
import com.ll.app20231218.global.exceptions.GlobalException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
    @Getter
    public static class LoginResponseBody {
        private String username;

        public LoginResponseBody(String username) {
            this.username = username;
        }
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
    public LoginResponseBody login(
        @Valid @RequestBody LoginRequestBody body
    ) {
        Member member = memberService.findByUsername(body.getUsername())
                .orElseThrow(() -> new GlobalException("400-1", "해당 사용자가 존재하지 않습니다."));

        if (memberService.passwordMatches(member, body.getPassword()) == false) {
            throw new GlobalException("400-2", "비밀번호가 일치하지 않습니다.");
        }

        return new LoginResponseBody(member.getUsername());
    }
}
