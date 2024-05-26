package com.szy.terminal.server;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

public class DockerConnect {

    public final DockerClient dockerClient;

    public DockerConnect() {
        DefaultDockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://localhost:2375")
                .withApiVersion("1.41")
                .build();
        this.dockerClient = DockerClientBuilder.getInstance(dockerClientConfig).build();
    }
}
