package com.adf.twolisteningports.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PortRoutingFilter implements Filter {
    private static final String INTERNAL_URI ="/internal";
    private static final String EXTERNAL_URI ="/external";
    private final Integer internalPort;
    private final Integer externalPort;

    public PortRoutingFilter(@Value("${server.internal-port}")
                             Integer internalPort,
                             @Value("${server.port}")
                             Integer externalPort) {
        this.internalPort = internalPort;
        this.externalPort = externalPort;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var requestPort = request.getServerPort();
        var requestUri = ((HttpServletRequest) request).getRequestURI();
        log.debug("Request URI: {}, requestPort: {}", requestUri, requestPort);

        // TODO: what about /actuators and other endpoints?

        if ((requestUri.startsWith(INTERNAL_URI) && internalPort.equals(requestPort)) ||
                (requestUri.startsWith(EXTERNAL_URI) && externalPort.equals(requestPort))) {
            chain.doFilter(request, response);
            return;
        }

        // If the request is not being accessed by the proper port, return 404
        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
