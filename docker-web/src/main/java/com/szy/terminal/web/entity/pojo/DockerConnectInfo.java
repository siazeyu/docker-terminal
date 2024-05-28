package com.szy.terminal.web.entity.pojo;

import com.pty4j.PtyProcess;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class DockerConnectInfo {

    private WebSocketSession webSocketSession;

    private PtyProcess ptyProcess;

    private String containerId;

}
