package com.scriptterror.bot.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "bot.proxy")
@Component
@Getter
@Setter
public class Proxy {
    private String host;
    private int port;
    private String user;
    private String password;


}
