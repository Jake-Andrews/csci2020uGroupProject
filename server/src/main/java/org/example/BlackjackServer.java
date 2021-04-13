package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Hashtable;
import java.util.LinkedList;

public class BlackjackServer {
    private static ServerSocket serverSocket;
    private static LinkedList<Thread> threads;
    private static Hashtable<Long, ClientHandler> handlers;
    private static Thread socketThread;

    public static void start() {
        //Boilerplate code was ripped from lab10 demo for handler and server. Changed as needed.
        System.out.println("Started server");
        int port = 8001;
        try {
            //creates the connection between the server and the client
            //This connection is put into ClientHandler
            serverSocket = new ServerSocket(port);
            threads = new LinkedList<Thread>();
            handlers = new Hashtable<Long, ClientHandler>();
            socketThread = new Thread(() -> {
                try {
                    while (true) {
                        var newSocket = serverSocket.accept();
                        var clientHandler = new ClientHandler(newSocket);
                        var thread = new Thread(clientHandler);
                        thread.start();
                        threads.add(thread);
                        handlers.put(thread.getId(), clientHandler);
                        System.out.println("New thread added to the server!");
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
            System.out.println("Now outside of the while loop");

            socketThread.start();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void stand() {
        //Go through linkedList of threads
        //
    }

    public void serverStop() throws IOException {
        System.out.println("Closing the server.");
        socketThread.interrupt();
        //just removes the sockets, is fine
        while (!threads.isEmpty()) {
            var t = threads.remove();
            handlers.get(t.getId()).stop();
            t.interrupt();
        }
        //Close our serverSocket and input stream
        serverSocket.close();
    }
}
