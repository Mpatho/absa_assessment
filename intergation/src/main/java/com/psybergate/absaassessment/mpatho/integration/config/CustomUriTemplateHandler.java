package com.psybergate.absaassessment.mpatho.integration.config;

import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class CustomUriTemplateHandler extends DefaultUriBuilderFactory {

    private final String scheme;
    private final String host;
    private final Integer port;

    public CustomUriTemplateHandler(String scheme, String host, Integer port) {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
    }

    @Override
    public URI expand(String uriTemplate, Object... uriVars) {
        URI expand = super.expand(uriTemplate, uriVars);
        try {
            return new URI(scheme, expand.getUserInfo(), host, port, "/api" + expand.getPath(), expand.getQuery(), expand.getFragment());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
