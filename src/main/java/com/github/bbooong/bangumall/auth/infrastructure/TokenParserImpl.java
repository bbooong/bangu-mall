package com.github.bbooong.bangumall.auth.infrastructure;

import com.github.bbooong.bangumall.auth.domain.ClaimType;
import com.github.bbooong.bangumall.auth.domain.Token;
import com.github.bbooong.bangumall.auth.domain.TokenParser;
import com.github.bbooong.bangumall.auth.exception.JwtInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenParserImpl implements TokenParser {

    private final io.jsonwebtoken.JwtParser parser;

    @Override
    public Token parse(final String header) {
        final String token = extract(header);

        try {
            final Claims claims = (Claims) parser.parse(token).getPayload();

            return Token.from(
                    Arrays.stream(ClaimType.values())
                            .filter(type -> claims.containsKey(type.getClaimName()))
                            .collect(
                                    Collectors.toMap(
                                            type -> type,
                                            type ->
                                                    claims.get(
                                                            type.getClaimName(), String.class))));
        } catch (final JwtException e) {
            throw new JwtInvalidException(e);
        }
    }

    String extract(final String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new JwtInvalidException();
        }

        return header.substring(7);
    }
}
