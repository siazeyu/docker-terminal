package com.szy.terminal.web.service;

import com.pty4j.PtyProcess;
import com.szy.terminal.web.entity.pojo.DockerConnectInfo;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.io.IOException;


/**
 * @author Siaze
 * @description: TODO
 * @date 2024/5/27 11:00
 */
public interface DockerService {

    void transTerminal(PtyProcess ptyProcess, String command) throws Exception;

    PtyProcess connect(String containerId);

    public void sendMessage(Session session, byte[] buffer) throws IOException;

    void close(Session session);

    void connectTerminal(DockerConnectInfo connectInfo, Session webSocketSession) throws IOException;
}
