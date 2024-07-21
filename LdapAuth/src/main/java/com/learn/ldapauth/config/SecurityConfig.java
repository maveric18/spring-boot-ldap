package com.learn.ldapauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                .anyRequest().authenticated()
                ).formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://localhost:10389");
        contextSource.setUserDn("uid=admin,ou=system");
        contextSource.setPassword("secret");
        return contextSource;
    }

    @Bean
    AuthenticationManager authenticationMacnager(BaseLdapPathContextSource baseLdapPathContextSource) {
        LdapBindAuthenticationManagerFactory managerFactory = new LdapBindAuthenticationManagerFactory(baseLdapPathContextSource);
        managerFactory.setUserDnPatterns("uid={0},ou=users,ou=system");
        return managerFactory.createAuthenticationManager();
    }
}
