package com.retrolad.mediatron.security;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "trusted-clients")
@Getter
@Setter
public class TrustedClientsProperties {

    @Data
    public static class ClientConfig {
        private String clientId;
        private String clientSecret;
        private String clientAuthority;
    }

    private Map<String, ClientConfig> clients;
}
