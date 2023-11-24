package com.szy.terminal.web;


import com.szy.terminal.web.session.TerminalSession;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DockerWebApplicationTests {

    @Test
    void contextLoads() {
        TerminalSession terminalSession = new TerminalSession();
//        DockerClient client = DockerUtils.connect();
//        AttachContainerCmd attached = client.attachContainerCmd("78124cf2c8a1");

    }

}
