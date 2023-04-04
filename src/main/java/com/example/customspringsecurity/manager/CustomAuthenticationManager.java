package com.example.customspringsecurity.manager;

import com.example.customspringsecurity.provider.ApikeyProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationManager implements AuthenticationManager {
    private final String key;

    public CustomAuthenticationManager(String key) {
        this.key = key;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var provider=new ApikeyProvider(key);
        if(provider.supports(authentication.getClass())){
         return   provider.authenticate(authentication);
        }
        return authentication;
    }
}
