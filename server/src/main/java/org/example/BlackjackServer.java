package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Hashtable;
import java.util.LinkedList;

public class BlackjackServer {
    public static void start(int port) {
        try {
            var serverSocket = new ServerSocket(port);
            var threads = new LinkedList<Thread>();
            var handlers = new Hashtable<Long, ClientHandler>();
            var socketThread = new Thread(() -> {
                try {
                    while (true) {
                        var newSocket = serverSocket.accept();
                        var clientHandler = new ClientHandler(newSocket);
                        var thread = new Thread(clientHandler);
                        thread.start();
                        threads.add(thread);
                        handlers.put(thread.getId(), clientHandler);
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
            var in = new BufferedReader(new InputStreamReader(System.in));

            String line;
            socketThread.start();
            while ((line = in.readLine()) == null || !line.equalsIgnoreCase("\\q"));
            socketThread.interrupt();

            while (!threads.isEmpty()) {
                var t = threads.remove();
                handlers.get(t.getId()).stop();
                t.interrupt();
            }

            serverSocket.close();
            in.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}


/**
 * Lab 10 Demo: server.MessageServer
 *
 * @author Michael Valdron
 * created at 2021/03/30
 */

