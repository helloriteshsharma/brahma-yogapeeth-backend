package com.brahma.yogapeeth.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "email")
public class EmailConfig {
    private String source;
    private String admin;

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getAdmin() { return admin; }
    public void setAdmin(String admin) { this.admin = admin; }
}
