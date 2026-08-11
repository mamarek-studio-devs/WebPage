package pl.mamarek.backend.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.webmvc.autoconfigure.DispatcherServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import tools.jackson.databind.json.JsonMapper;
import pl.mamarek.backend.servlet.TomcatEchoServlet;

import java.nio.charset.StandardCharsets;

@Configuration
public class TomcatConfiguration {

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

        factory.addConnectorCustomizers(this::customizeConnector);
        factory.addContextCustomizers(this::customizeContext);

        return factory;
    }

    @Bean(name = "dispatcherServlet")
    public DispatcherServlet dispatcherServlet() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setDispatchOptionsRequest(true);
        return dispatcherServlet;
    }

    @Bean(name = "dispatcherServletRegistration")
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
        DispatcherServletRegistrationBean registrationBean =
                new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        registrationBean.setName("mamarekDispatcherServlet");
        registrationBean.setLoadOnStartup(1);
        registrationBean.addInitParameter("customTomcatMarker", "mamarek-configured");
        return registrationBean;
    }

    @Bean
    public ServletRegistrationBean<TomcatEchoServlet> tomcatEchoServlet(JsonMapper jsonMapper) {
        ServletRegistrationBean<TomcatEchoServlet> registrationBean =
                new ServletRegistrationBean<>(new TomcatEchoServlet(jsonMapper), "/api/server/tomcat/manual");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

    private void customizeConnector(Connector connector) {
        connector.setURIEncoding(StandardCharsets.UTF_8.name());
        connector.setProperty("relaxedPathChars", "[]|{}^`<>");
        connector.setProperty("relaxedQueryChars", "[]|{}^`<>");
        connector.setProperty("connectionTimeout", "20000");
    }

    private void customizeContext(Context context) {
        context.setDisplayName("Mamarek Custom Tomcat");
        context.setSessionTimeout(30);
        context.setUseHttpOnly(true);
    }
}
