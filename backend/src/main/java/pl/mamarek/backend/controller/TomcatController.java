package pl.mamarek.backend.controller;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.tomcat.TomcatWebServer;
import org.springframework.boot.web.server.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mamarek.backend.model.DispatcherServletInfoDto;
import pl.mamarek.backend.model.TomcatInfoDto;

@RestController
@RequestMapping("/api/server")
@RequiredArgsConstructor
public class TomcatController {

    private static final String DISPATCHER_SERVLET_NAME = "mamarekDispatcherServlet";
    private static final String DISPATCHER_SERVLET_PATH = "/";
    private static final String DISPATCHER_INIT_MARKER = "mamarek-configured";

    private final ApplicationContext applicationContext;

    @GetMapping("/tomcat")
    public ResponseEntity<TomcatInfoDto> getTomcatInfo() {
        ServletWebServerApplicationContext servletWebServerApplicationContext =
                (ServletWebServerApplicationContext) applicationContext;
        TomcatWebServer tomcatWebServer = (TomcatWebServer) servletWebServerApplicationContext.getWebServer();
        Connector connector = tomcatWebServer.getTomcat().getConnector();

        TomcatInfoDto response = new TomcatInfoDto(
                servletWebServerApplicationContext.getServletContext().getServletContextName(),
                tomcatWebServer.getTomcat().getServer().getClass().getSimpleName(),
                connector.getPort(),
                connector.getProtocol(),
                true
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/dispatcher")
    public ResponseEntity<DispatcherServletInfoDto> getDispatcherServletInfo() {
        DispatcherServletInfoDto response = new DispatcherServletInfoDto(
                DISPATCHER_SERVLET_NAME,
                DISPATCHER_SERVLET_PATH,
                DISPATCHER_INIT_MARKER,
                "DispatcherServlet is explicitly configured and registered by our app"
        );

        return ResponseEntity.ok(response);
    }
}
