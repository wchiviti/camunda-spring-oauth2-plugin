package io.github.hobbstech.camunda.oauth2.plugin.filter;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.util.EngineUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class StatelessUserAuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        /*
            Do nothing.
         */
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        ProcessEngine engine = EngineUtil.lookupProcessEngine("default");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        try {
            engine.getIdentityService().setAuthentication(username, getUserGroups(username));
            chain.doFilter(request, response);
        } finally {
            clearAuthentication(engine);
        }

    }

    @Override
    public void destroy() {

    }

    private void clearAuthentication(ProcessEngine engine) {
        engine.getIdentityService().clearAuthentication();
    }

    private List<String> getUserGroups(String userId) {

        List<String> groupIds;

        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        groupIds = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(res -> res.substring(5)) // Strip "ROLE_"
                .collect(Collectors.toList());

        return groupIds;

    }

}
