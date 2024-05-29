package com.szy.terminal.web.service.impl;

import com.pty4j.PtyProcess;
import com.szy.terminal.server.DockerConnect;
import com.szy.terminal.web.entity.pojo.DockerConnectInfo;
import com.szy.terminal.web.service.DockerService;
import com.szy.terminal.web.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Siaze
 * @description: TODO
 * @date 2024/5/27 11:00
 */

@Slf4j
public class DockerServiceImpl implements DockerService {

    private DockerConnect dockerConnect = SpringUtils.getBean(DockerConnect.class);

    public void transTerminal(PtyProcess ptyProcess, String command) throws IOException {
        if (ptyProcess != null) {
            OutputStream outputStream = ptyProcess.getOutputStream();
            outputStream.write(command.getBytes());
            outputStream.flush();
        }
    }

    public PtyProcess connect(String containerId) {
        return dockerConnect.connectTerminal(containerId);
    }

    @Override
    public void sendMessage(Session session, byte[] buffer) throws IOException {
        session.getAsyncRemote().sendText(new String(buffer));
    }

    @Override
    public void close(Session session) {

    }

    @Override
    public void connectTerminal(DockerConnectInfo connectInfo, Session webSocketSession) throws IOException {
        if (Objects.isNull(connectInfo.getPtyProcess())) {
            connectInfo.setPtyProcess(connect(connectInfo.getContainerId()));
        }
        PtyProcess ptyProcess = connectInfo.getPtyProcess();
        //转发消息
//        transTerminal(ptyProcess, "\r");

        //读取终端返回的信息流
        try (InputStream inputStream = ptyProcess.getInputStream()) {
            //循环读取
            byte[] buffer = new byte[1024];
            int i = 0;
            //如果没有数据来，线程会一直阻塞在这个地方等待数据。
            while ((i = inputStream.read(buffer)) != -1) {
                sendMessage(webSocketSession, Arrays.copyOfRange(buffer, 0, i));
            }
            log.info("释放连接 {}", connectInfo.getContainerId());
        } finally {
            //断开连接后关闭会话
            ptyProcess.destroy();
        }
    }
}