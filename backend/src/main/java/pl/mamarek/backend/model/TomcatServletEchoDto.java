package pl.mamarek.backend.model;

import lombok.Builder;

@Builder
public record TomcatServletEchoDto(
        String configuredName,
        String method,
        String requestUri,
        String servletPath,
        String contextPath,
        String message
) {
}
