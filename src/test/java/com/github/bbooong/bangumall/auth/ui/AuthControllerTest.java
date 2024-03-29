package com.github.bbooong.bangumall.auth.ui;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import com.github.bbooong.bangumall.config.AcceptanceTest;
import com.github.bbooong.bangumall.fixture.MemberFixture;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("인수 테스트: 인증")
@AcceptanceTest
class AuthControllerTest {

    @Nested
    @DisplayName("로그인 할 때")
    class Describe_Login {

        @Nested
        @DisplayName("이메일, 비밀번호를 알맞게 입력한 경우")
        class Context_With_ValidRequest {

            final String email = "test@email.com";
            final String password = "test";
            final String role = "CUSTOMER";

            @BeforeEach
            public void init() {
                MemberFixture.createMember(email, password, role);
            }

            @Test
            @DisplayName("토큰을 반환한다.")
            void it_returns_token() {
                RestAssured.given()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(
                                """
                                        {
                                            "email": "%s",
                                            "password": "%s"
                                        }
                                        """
                                        .formatted(email, password))
                        .when()
                        .post("/auth/login")
                        .then()
                        .statusCode(HttpStatus.OK.value())
                        .body("token", is(not(emptyString())));
            }
        }
    }
}
