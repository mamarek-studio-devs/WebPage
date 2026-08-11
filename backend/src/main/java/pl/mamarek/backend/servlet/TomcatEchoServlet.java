package pl.mamarek.backend.servlet;

import lombok.RequiredArgsConstructor;
import pl.mamarek.backend.model.TomcatServletEchoDto;
import tools.jackson.databind.json.JsonMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@RequiredArgsConstructor
public class TomcatEchoServlet extends HttpServlet {

    private final JsonMapper jsonMapper;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        writeEchoResponse(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        writeEchoResponse(req, resp);
    }

    private void writeEchoResponse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TomcatServletEchoDto body = TomcatServletEchoDto.builder()
                .configuredName(req.getServletContext().getServletContextName())
                .method(req.getMethod())
                .requestUri(req.getRequestURI())
                .servletPath(req.getServletPath())
                .contextPath(req.getContextPath())
                .message("Handled directly by the custom Tomcat servlet")
                .build();

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        jsonMapper.writeValue(resp.getWriter(), body);
    }
}
