package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BlackjackClient {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
//create the object with the socket given
    private BlackjackClient(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintWriter(this.socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public static BlackjackClient connect(String host, int port) {
        try {
            return new BlackjackClient(new Socket(host, port));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public boolean isAlive() {
        return socket.isConnected();
    }

    public String sendMessage(String msg) {
        String res = null;
        System.out.println("Sending to server: " + msg);
        out.println(msg);

        try {
            System.out.println("Read from server:");
            res = in.readLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return res;
    }

    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        out.close();

        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}