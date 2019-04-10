package com.mdem.cms.config;

import com.mdem.cms.security.TokenAuthenticationFilter;
import com.mdem.cms.security.TokenAuthenticationManager;
import com.mdem.cms.security.AuthenticationAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.mdem.cms")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySources({
        @PropertySource("classpath:security.properties"),
        @PropertySource("classpath:urls.properties")
})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${urls.loginUrl}")
    private String LOGIN_URL;           // /login

    @Value("${urls.createUserUrl}")
    private String CREATE_USER_URL;     // /user/create

    @Value("${urls.secureUrl}")
    private String SECURE_URL;          // /api/**

    private AbstractAuthenticationProcessingFilter tokenAuthenticationFilter;
    private TokenAuthenticationManager tokenAuthenticationManager;
    private AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

    @Autowired
    public void setTokenAuthenticationFilter(AbstractAuthenticationProcessingFilter tokenAuthenticationFilter) {
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
    }

    @Autowired
    public void setTokenAuthenticationManager(TokenAuthenticationManager tokenAuthenticationManager) {
        this.tokenAuthenticationManager = tokenAuthenticationManager;
    }

    @Autowired
    public void setAuthenticationAccessDeniedHandler(AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler) {
        this.authenticationAccessDeniedHandler = authenticationAccessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/*", "/resources/**", "/v2/api-docs", "/webjars/**", "/swagger-resources/**").permitAll()
                .antMatchers(HttpMethod.POST, LOGIN_URL, CREATE_USER_URL).permitAll()
                .anyRequest().authenticated().and()
                .httpBasic().disable()
                .formLogin().disable()
                .rememberMe().disable()
                .addFilterBefore(tokenAuthenticationFilter, BasicAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(authenticationAccessDeniedHandler);
    }

    @Bean
    public AbstractAuthenticationProcessingFilter getTokenAuthenticationFilter() {
        TokenAuthenticationFilter filter = new TokenAuthenticationFilter(SECURE_URL);
        filter.setAuthenticationManager(tokenAuthenticationManager);
        return filter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.addExposedHeader("Authorization");
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}