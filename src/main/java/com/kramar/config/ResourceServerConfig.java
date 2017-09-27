package com.kramar.config;

import com.kramar.data.type.UserRole;
import com.kramar.data.web.AdvertController;
import com.kramar.security.web.HeartbeatController;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@EnableWebSecurity
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("webresource");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers(HeartbeatController.REQUEST_MAPPING + "/**").permitAll()
                .antMatchers(AdvertController.REQUEST_MAPPING + "/**").hasAnyAuthority(UserRole.VALIDATED_USER.name(), UserRole.ADMIN.name(), UserRole.SUPER_ADMIN.name())
                .anyRequest().permitAll();
    }
}