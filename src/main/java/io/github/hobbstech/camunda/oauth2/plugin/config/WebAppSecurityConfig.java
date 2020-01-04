package io.github.hobbstech.camunda.oauth2.plugin.config;

import io.github.hobbstech.camunda.oauth2.plugin.filter.SpringSecurityAuthenticationProvider;
import org.camunda.bpm.webapp.impl.security.auth.ContainerBasedAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Collections;

@Order(SecurityProperties.BASIC_AUTH_ORDER - 15)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/app/**", "/app/cockpit/**", "/api/engine/**", "/lib/**");
    }

    @Bean
    public FilterRegistrationBean containerBasedAuthenticationFilter() {

        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new ContainerBasedAuthenticationFilter());
        filterRegistration.setInitParameters(Collections.singletonMap("authentication-provider",
                SpringSecurityAuthenticationProvider.class.getName()));
        filterRegistration.setOrder(101);
        filterRegistration.addUrlPatterns("/app/**");
        return filterRegistration;
    }
}
