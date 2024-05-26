import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;
import com.szy.terminal.server.InputStreamReceiver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TestMain {

    public static void main(String[] args) throws Exception {
        PtyProcess process = new PtyProcessBuilder().setCommand(new String[]{"docker", "exec", "-it", "78124cf2c8a1", "/bin/bash"})
                .setInitialRows(37)
                .setInitialColumns(97).start();
        InputStream inputStream = process.getInputStream();
        OutputStream outputStream = process.getOutputStream();

        InputStreamReceiver outputStreamReceiver = new InputStreamReceiver(inputStream) {
            @Override
            public void doEvent(byte[] data) {
                System.out.print(new String(data));
            }

            @Override
            public void doError(Exception e) {

            }
        };
        outputStreamReceiver.start();

        new InputStreamReceiver(System.in) {
            @Override
            public void doEvent(byte[] data) {
                try {
                    outputStream.write(data);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void doError(Exception e) {

            }
        }.start();


        System.out.println("容器启动成功！");
    }

}
