package com.szy.terminal.web.config.docker;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class DockerConfig {

    @Value("${docker.host}")
    private String host;

    @Value("${docker.api-version}")
    private String apiVersion;


}
