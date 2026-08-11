package pl.mamarek.backend.model;

public record DispatcherServletInfoDto(
        String servletName,
        String path,
        String customInitParameter,
        String message
) {
}
