package com.retrolad.mediatron.security;

import com.retrolad.mediatron.exception.ClientNotValidException;
import com.retrolad.mediatron.security.TrustedClientsProperties.ClientConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientValidationService {

    private final TrustedClientsProperties clientsProperties;

    /**
     * Сверяет id и ключ клиента с данными доверенных клиентов
     * @param client данные клиента
     * @return являются ли данные клиента валидными
     */
    public boolean isClientValid(ClientConfig client) {
        String clientId = client.getClientId();
        String clientSecret = client.getClientSecret();

        // Проверить, что клиент является доверенным и его ключ совпадает
        if (!isClientTrusted(clientId)) {
            return false;
        }

        ClientConfig trustedClientConfig = clientsProperties.getClients().get(clientId);
        if (!trustedClientConfig.getClientSecret().equals(clientSecret)) {
            return false;
        }

        return true;
    }

    /**
     * Проверяет является клиент доверенным
     * @param clientId идентификатор клиента
     * @return является ли клиент валидным
     */
    public boolean isClientTrusted(String clientId) {
        return clientsProperties.getClients().containsKey(clientId);
    }

    public String getClientAuthority(ClientConfig client) {
        if (!isClientTrusted(client.getClientId())) {
            throw new ClientNotValidException("Клиент не является доверенным");
        }

        return clientsProperties.getClients().get(client.getClientId())
                .getClientAuthority();
    }
}
