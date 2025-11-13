package com.example.notification.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "inbox")
public class InboxProperties {
    private int batchSize;
    private long delayMs;
}
