package com.retrolad.mediatron.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ClientAuthenticationToken extends AbstractAuthenticationToken {

    private final String clientName;

    public ClientAuthenticationToken(String clientName, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.clientName = clientName;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return clientName;
    }
}
