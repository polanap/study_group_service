package org.example.study_group_service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.ee10.websocket.server.config.JettyWebSocketServletContainerInitializer;
import org.eclipse.jetty.server.Server;
import org.example.study_group_service.config.RootConfig;
import org.example.study_group_service.config.WebConfig;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ((Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)).setLevel(Level.INFO);
        ((Logger) LoggerFactory.getLogger("org.eclipse.jetty")).setLevel(Level.INFO);
        ((Logger) LoggerFactory.getLogger("org.springframework")).setLevel(Level.INFO);

        ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ctx.setContextPath("/");
        server.setHandler(ctx);

        // WebSocket
        JettyWebSocketServletContainerInitializer.configure(ctx, null);

        AnnotationConfigWebApplicationContext rootCtx = new AnnotationConfigWebApplicationContext();
        rootCtx.register(RootConfig.class);
        ctx.addEventListener(new ContextLoaderListener(rootCtx));

        AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
        webCtx.register(WebConfig.class);
        webCtx.setParent(rootCtx);

        ServletHolder holder = new ServletHolder(new DispatcherServlet(webCtx));
        holder.setInitOrder(1);
        ctx.addServlet(holder, "/*");

        server.start();
        server.join();
    }
}
