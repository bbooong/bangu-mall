package com.github.bbooong.bangumall.auth.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("단위 테스트: TokenManger")
class TokenManagerTest {

    private TokenManager tokenManager;

    @BeforeEach
    void init() {
        tokenManager = new TokenManager();
    }

    @Nested
    @DisplayName("token을 생성할 때")
    class Describe_GenerateToken {

        @Nested
        @DisplayName("subject가 존재하는 경우")
        class Context_With_Subject {

            final Long subject = 1L;

            @Test
            @DisplayName("token을 반환한다.")
            void it_returns_token() {
                final String token = tokenManager.generateToken(subject);
                assertThat(token).isNotEmpty();
            }
        }
    }
}
