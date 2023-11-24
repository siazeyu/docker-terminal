package com.szy.terminal.server;

import java.io.IOException;
import java.io.InputStream;

public abstract class OutputStreamReceiver implements StreamReceiver{

    private InputStream inputStream;

    @Override
    public void run() {
        while (true){
            try {
                //读入数据
                int length = inputStream.available();
                if (length == 0) continue;
                byte[] data = new byte[length];
                inputStream.read(data);
                doEvent(data);
            }catch (IOException i){

            }
        }
    }
    public abstract void doEvent(byte[] data);

}
