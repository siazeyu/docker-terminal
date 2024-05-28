package com.szy.terminal.web.controller;

import com.github.dockerjava.api.model.Container;
import com.szy.terminal.server.DockerConnect;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Siaze
 * @description: TODO
 * @date 2024/5/27 11:08
 */

@RestController
@RequestMapping("/docker")
public class DockerController {

    @Resource
    private DockerConnect dockerConnect;

    @GetMapping("/container")
    public List<Container> getContainers() {
        return dockerConnect.getAllContainers();
    }

    @PostMapping("/stop/{containerId}")
    public void stopContainer(@PathVariable("containerId") String containerId) {
        dockerConnect.stopContainer(containerId);
    }

    @PostMapping("/start/{containerId}")
    public void startContainer(@PathVariable("containerId") String containerId) {
        dockerConnect.startContains(containerId);
    }

}
