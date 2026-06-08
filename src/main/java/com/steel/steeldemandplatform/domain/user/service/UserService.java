package com.steel.steeldemandplatform.domain.user.service;

import com.steel.steeldemandplatform.domain.user.dto.SignupRequest;
import com.steel.steeldemandplatform.domain.user.entity.User;
import com.steel.steeldemandplatform.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.steel.steeldemandplatform.domain.user.dto.LoginRequest;
/*
 * 회원 관련 비즈니스 로직
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequest request) {

        if (userRepository.findByEmail(
                request.getEmail()
        ).isPresent()) {

            throw new RuntimeException(
                    "이미 존재하는 이메일입니다."
            );
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .companyName(request.getCompanyName())
                .role("USER")
                .build();

        userRepository.save(user);
    }
    /*
     * 로그인
     */
    public User login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("존재하지 않는 이메일입니다.")
                );

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!matches) {
            throw new RuntimeException(
                    "비밀번호가 일치하지 않습니다."
            );
        }

        return user;
    }
}