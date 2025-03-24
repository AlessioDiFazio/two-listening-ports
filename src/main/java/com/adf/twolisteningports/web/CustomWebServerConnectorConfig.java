package com.adf.twolisteningports.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CustomWebServerConnectorConfig implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    private final Integer internalPort;

    public CustomWebServerConnectorConfig(@Value("${server.internal-port}")
                                          Integer internalPort) {
        this.internalPort = internalPort;
    }

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        log.info("Adding internal Tomcat connector on port {}", internalPort);
        var additionalConnector = new Connector();
        additionalConnector.setPort(internalPort);
        factory.addAdditionalTomcatConnectors(additionalConnector);
    }
}
