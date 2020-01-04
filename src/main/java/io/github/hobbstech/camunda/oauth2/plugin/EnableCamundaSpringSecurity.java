package io.github.hobbstech.camunda.oauth2.plugin;

import io.github.hobbstech.camunda.oauth2.plugin.config.RestSecurityConfig;
import io.github.hobbstech.camunda.oauth2.plugin.config.WebAppSecurityConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Import({RestSecurityConfig.class, WebAppSecurityConfig.class})
public @interface EnableCamundaSpringSecurity {
}
