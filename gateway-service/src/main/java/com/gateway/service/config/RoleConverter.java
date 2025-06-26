package com.gateway.service.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Map<String, Object> realm_access = (Map<String, Object>) source.getClaims().get("realm_access");
        if (realm_access == null || realm_access.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> roles = (List<String>) realm_access.get("roles");
        // "ROLE_" is not required when we have "hasRole()" as it adds it self.
        Collection<GrantedAuthority> collect = roles.stream().map(role -> "ROLE_" + role)
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
        return collect;
    }
}