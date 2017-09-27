//package com.kramar.config;
//
//import com.kramar.security.web.HeartbeatController;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//
//@Configuration
//@EnableResourceServer
//public class SecurityResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.resourceId("authresource");
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests()
//                .antMatchers("/swagger-ui.html").permitAll()
//                .antMatchers(HeartbeatController.REQUEST_MAPPING + "/**").permitAll()
//                .anyRequest().authenticated()
//                .and().csrf().disable();
//    }
//
////    @Override
////    public void configure(WebSecurity web) throws Exception {
////        web.ignoring()
////                .antMatchers(HttpMethod.OPTIONS, "/**")
////                .antMatchers("/app/**/*.{js,html}")
////                .antMatchers("/bower_components/**")
////                .antMatchers("/i18n/**")
////                .antMatchers("/content/**")
////                .antMatchers("/swagger-ui/index.html")
////                .antMatchers("/test/**")
////                .antMatchers("/h2-console/**");
////    }
//}
