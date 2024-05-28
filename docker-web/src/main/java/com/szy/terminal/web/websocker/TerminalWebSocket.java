package com.szy.terminal.web.websocker;

import com.pty4j.PtyProcess;
import com.szy.terminal.web.entity.pojo.DockerConnectInfo;
import com.szy.terminal.web.service.DockerService;
import com.szy.terminal.web.service.impl.DockerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@Service
@ServerEndpoint("/websocket/terminal/{containerId}")
public class TerminalWebSocket {

    private static final Map<Session, DockerConnectInfo> maps = new ConcurrentHashMap<>();
    /**
     * 线程安全的无序的集合
     */
    private static final CopyOnWriteArraySet<Session> SESSIONS = new CopyOnWriteArraySet<>();
    private final DockerService dockerService = new DockerServiceImpl();
    /**
     * 线程池
     */
    private final ExecutorService executorService = Executors.newCachedThreadPool();


    @OnOpen
    public void onOpen(Session session, @PathParam("containerId") String containerId) {
        try {
            SESSIONS.add(session);
            DockerConnectInfo dockerConnectInfo = new DockerConnectInfo();
            dockerConnectInfo.setContainerId(containerId);
            dockerService.connect(containerId);
            maps.put(session, dockerConnectInfo);
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        dockerService.connectTerminal(dockerConnectInfo, session);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            log.info("【WebSocket消息】有新的连接，总数为：" + SESSIONS.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        try {
            DockerConnectInfo dockerConnectInfo = maps.get(session);
            if (Objects.isNull(dockerConnectInfo)) {
                return;
            }
            PtyProcess ptyProcess = dockerConnectInfo.getPtyProcess();
            if (ptyProcess.isAlive()) {
                ptyProcess.destroy();
            }
            SESSIONS.remove(session);
            maps.remove(session);
            log.info("【WebSocket消息】连接断开，总数为：" + SESSIONS.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("【WebSocket消息】收到客户端消息：" + message);
        System.out.println(message);
        try {
            dockerService.transTerminal(maps.get(session).getPtyProcess(), message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 此为广播消息
     *
     * @param message 消息
     */
    public void sendAllMessage(String message) {
        log.info("【WebSocket消息】广播消息：" + message);

    }

    /**
     * 此为单点消息
     *
     * @param userId  用户编号
     * @param message 消息
     */
    public void sendOneMessage(String userId, String message) {

    }

    /**
     * 此为单点消息(多人)
     *
     * @param userIds 用户编号列表
     * @param message 消息
     */
    public void sendMoreMessage(String[] userIds, String message) {

    }


}
