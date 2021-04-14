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
        /**
         * This method instantiates the socket, and the in and output streams
         * the socket reads and writes to.
         * @param socket    The socket we connect to
         */
        this.socket = socket;
        out = new PrintWriter(this.socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public static BlackjackClient connect(String host, int port) {
        /**
         * creates a new client when called
         * @param host  local host of the client
         * @param port  port number to listen to
         */
        try {
            return new BlackjackClient(new Socket(host, port));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public boolean isAlive() {
        /**
         * @return true if the socket is still connected.
         */
        return socket.isConnected();
    }

    public String sendMessage(String msg) {
        /**Writes a message to the socket outstream.
         * @param msg   The message to write
         * @return the string read in from the server
         */
        String res = null;
        System.out.println("Sending to server: " + msg);
        out.println(msg);

        try {
            System.out.println("Read from server:");
            res = in.readLine();
            System.out.println(res);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return res;
    }

    public void close() {
        /**
         * Closes the input stream, and the socket
         * for best coding practices.
         */
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