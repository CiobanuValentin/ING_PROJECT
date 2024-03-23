package com.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ClockConfig {

    public static final String UTC_CLOCK = "system.clock.utc";

    @Bean(UTC_CLOCK)
    public Clock clock() {
        return Clock.systemUTC();
    }
}
