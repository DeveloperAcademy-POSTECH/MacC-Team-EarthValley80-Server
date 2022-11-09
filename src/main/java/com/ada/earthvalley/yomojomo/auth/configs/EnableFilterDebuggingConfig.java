package com.ada.earthvalley.yomojomo.auth.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Profile("filters-registered")
@EnableWebSecurity(debug = true)
@Configuration
public class EnableFilterDebuggingConfig {
}
