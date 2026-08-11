package pl.mamarek.backend.model;

public record TomcatInfoDto(
        String configuredName,
        String serverName,
        int port,
        String protocol,
        boolean running
) {
}
