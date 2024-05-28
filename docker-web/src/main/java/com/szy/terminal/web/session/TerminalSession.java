package com.szy.terminal.web.session;

import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Getter
@Slf4j
public class TerminalSession {

    private String name;

    private OutputStream writer;

    private InputStream error;

    private InputStream input;

    private PtyProcess process = null;

    public TerminalSession() {
        initialization();
    }

    public void initialization(){
        try {
            PtyProcess process = new PtyProcessBuilder().setCommand(new String[]{"docker exec -it 78124cf2c8a1 /bin/bash"}).start();
            this.process = process;
            int exitValue = process.waitFor();
            if (exitValue != 0) {
                // 读取错误流
                error = process.getErrorStream();
                log.error("{} 容器启动失败！", getName());
                return;
            }
            log.info("{} 容器启动成功！", getName());
        } catch (Exception e) {
            throw new RuntimeException(String.format("%s 容器启动失败！原因：%s", getName(), e));
        }
    }

    public void setName(String name) {
        this.name = name;
    }

}
