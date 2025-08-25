package com.keerthana.bank_app.configuration;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class NoCacheFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResp = (HttpServletResponse) response;
        httpResp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        httpResp.setHeader("Pragma", "no-cache");
        httpResp.setDateHeader("Expires", 0);
        chain.doFilter(request, response);
    }
}