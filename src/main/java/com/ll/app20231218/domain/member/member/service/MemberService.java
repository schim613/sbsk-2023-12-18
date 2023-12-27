package com.ll.app20231218.domain.member.member.service;

import com.ll.app20231218.domain.member.member.entity.Member;
import com.ll.app20231218.domain.member.member.repository.MemberRepository;
import com.ll.app20231218.global.exceptions.GlobalException;
import com.ll.app20231218.global.rsData.RsData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenService authTokenService;

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Transactional
    public void join(String username, String password) {
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();

        memberRepository.save(member);
    }

    public boolean passwordMatches(Member member, String password) {
        return passwordEncoder.matches(password, member.getPassword());
    }

    @AllArgsConstructor
    @Getter
    public static class AuthAndMakeTonkensResponseBody {
        private Member member;
        private String accessToken;
        private String refreshToken;
    }

    @Transactional
    public RsData<AuthAndMakeTonkensResponseBody> authAndMakeTokens(String username, String password) {
        Member member = findByUsername(username)
                .orElseThrow(() -> new GlobalException("400-1", "해당 사용자가 존재하지 않습니다."));

        if (!passwordMatches(member, password))
            throw new GlobalException("400-2", "비밀번호가 일치하지 않습니다.");

        String accessToken = authTokenService.genAccessToken(member);
        String refreshToken = authTokenService.genRefreshToken(member);

        return RsData.of(
                "200-1",
                "로그인 성공",
                new AuthAndMakeTonkensResponseBody(member, accessToken, refreshToken)
        );
    }
}
