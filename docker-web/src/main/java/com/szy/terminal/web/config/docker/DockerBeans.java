package com.szy.terminal.web.config.docker;


import com.szy.terminal.server.DockerConnect;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Order(-1)
public class DockerBeans {

    @Resource
    private DockerConfig dockerConfig;

    @Bean
    public DockerConnect dockerConnect() {
        return new DockerConnect(dockerConfig.getHost());
    }

}
