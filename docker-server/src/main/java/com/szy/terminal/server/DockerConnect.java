package com.szy.terminal.server;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

import java.util.List;

public class DockerConnect {

    public final DockerClient dockerClient;

    public DockerConnect(String host) {
        DefaultDockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(host)  // tcp://localhost:2375
//                .withApiVersion(api) // 1.41
                .build();
        this.dockerClient = DockerClientBuilder.getInstance(dockerClientConfig).build();
    }

    public Info getInfo() {
        return dockerClient.infoCmd().exec();
    }

    public List<Container> getContainers(boolean showAll) {
        return dockerClient.listContainersCmd().withShowAll(showAll).exec();
    }

    public List<Container> getRunningContainers() {
        return getContainers(false);
    }

    public List<Container> getAllContainers() {
        return getContainers(true);
    }

    public void startContains(String id) {
        List<Container> runningContainers = getRunningContainers();
        for (Container runningContainer : runningContainers) {
            if (runningContainer.getId().startsWith(id))
                return;
        }
        dockerClient.startContainerCmd(id).exec();
    }

    public CreateContainerResponse createCentosContainer(String containerName) {
        return createContainer("centos:7", containerName);
    }

    public CreateContainerResponse createUbuntuContainer(String containerName) {
        return createContainer("ubuntu:latest", containerName);
    }

    public CreateContainerResponse createContainer(String imageName, String containerName) {
        return dockerClient.createContainerCmd(imageName)
                .withStdinOpen(true)
                .withCmd("/bin/bash").withName(containerName).exec();
    }

    public void deleteContainer(String containerId) {
        getRunningContainers().forEach(container -> {
            if (container.getId().equals(containerId)) stopContainer(containerId);
        });
        dockerClient.removeContainerCmd(containerId).exec();
    }

    public void stopContainer(String containerId) {
        dockerClient.stopContainerCmd(containerId).exec();
    }
}