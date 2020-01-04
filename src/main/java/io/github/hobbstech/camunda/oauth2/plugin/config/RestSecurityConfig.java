package io.github.hobbstech.camunda.oauth2.plugin.config;

import io.github.hobbstech.camunda.oauth2.plugin.filter.StatelessUserAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Order(SecurityProperties.BASIC_AUTH_ORDER - 20)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/v1/**")
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic();

    }

    @Bean
    public FilterRegistrationBean statelessUserAuthenticationFilter(){
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new StatelessUserAuthenticationFilter());
        filterRegistration.setOrder(102);
        filterRegistration.addUrlPatterns("/v1/**");
        return filterRegistration;
    }

}
