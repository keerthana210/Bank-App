package com.keerthana.bank_app.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalSecurityExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized",
                authException.getMessage(), request.getRequestURI());
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        writeErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Forbidden",
                accessDeniedException.getMessage(), request.getRequestURI());
    }

    private void writeErrorResponse(HttpServletResponse response, int status, String error,
                                    String message, String path) throws IOException {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now().toString());
        errorDetails.put("status", status);
        errorDetails.put("error", error);
        errorDetails.put("message", message != null ? message : "");
        errorDetails.put("path", path);
        response.setContentType("application/json");
        response.setStatus(status);
        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
    }
}
