package com.zerobase.reservation.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.reservation.exception.ErrorCode;
import com.zerobase.reservation.exception.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {

        String token = this.resolveTokenFromRequest(request);

        try {

            if (StringUtils.hasText(token) && this.tokenProvider.validateToken(token)) {
                Authentication auth = this.tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            filterChain.doFilter(request, response);

        } catch (MalformedJwtException | UnsupportedJwtException | SignatureException e) {
            sendErrorResponse(response, "지원하지 않는 토큰입니다.");
        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, "만료된 토큰입니다.");
        }

    }

    private String resolveTokenFromRequest(HttpServletRequest request) {

        String token = request.getHeader(TOKEN_HEADER);

        if (!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    /**
     * Filter 단계에서 내려주는 에러응답
     */
    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {

        log.error("{} errorResponse : {}", getClass(), message);

        ObjectMapper objectMapper = new ObjectMapper();
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper
                                    .writeValueAsString(ErrorResponse.builder()
                                                                    .errorCode(ErrorCode.INVALID_REQUEST)
                                                                    .errorMessage(message)
                                                                    .build()));

    }
}
