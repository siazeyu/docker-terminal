package com.szy.terminal.server;

public interface StreamReceiver extends Runnable{
    void doEvent(byte[] date);
}
