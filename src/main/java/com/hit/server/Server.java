package com.hit.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.hit.services.CacheUnitController;

public class Server implements PropertyChangeListener, Runnable {

    private ServerSocket serverSocket = null;
    private String command;
    boolean isrunning;
    boolean flag;

    public Server() {
        isrunning = false;
        flag = true;
    }

    @Override
    public void run() {
        Executor executor = Executors.newFixedThreadPool(1);
        CacheUnitController<String> cacheUnitController = new CacheUnitController<>();
        try {

            while (true) {
                if (flag) {
                    serverSocket = new ServerSocket(12345);
                    flag = false;
                }
                if (!serverSocket.isClosed() && command.equalsIgnoreCase("start")) { // CLI open the server
                    Socket socket = serverSocket.accept();
                    HandleRequest<String> handleRequest = new HandleRequest<>(socket, cacheUnitController);
                    executor.execute(handleRequest);
                }
                if (command.equalsIgnoreCase("stop")) { // CLI close the server
                    if (!serverSocket.isClosed()) {
                        serverSocket.close();
                    }
                }
            }
        } catch (IOException e) {
            try {
                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    //event from CLI
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        command = (String) evt.getNewValue();
        flag = command.equalsIgnoreCase("start");
        if (!isrunning) {
            new Thread(this).start();
            isrunning = true;
        }

    }

}
