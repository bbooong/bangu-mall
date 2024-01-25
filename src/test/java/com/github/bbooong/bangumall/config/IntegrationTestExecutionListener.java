package com.github.bbooong.bangumall.config;

import static io.restassured.filter.log.LogDetail.ALL;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class IntegrationTestExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestClass(final TestContext testContext) {
        RestAssured.port =
                Optional.ofNullable(
                                testContext
                                        .getApplicationContext()
                                        .getEnvironment()
                                        .getProperty("local.server.port", Integer.class))
                        .orElseThrow(
                                () -> new IllegalStateException("localServerPort는 null일 수 없습니다."));

        RestAssured.config =
                RestAssured.config()
                        .logConfig(
                                LogConfig.logConfig()
                                        .enableLoggingOfRequestAndResponseIfValidationFails(ALL)
                                        .enablePrettyPrinting(true))
                        .encoderConfig(
                                EncoderConfig.encoderConfig()
                                        .defaultCharsetForContentType(
                                                StandardCharsets.UTF_8.name(), ContentType.ANY));
    }
}
