package io.noahele.whiteboard;

import java.io.*;
import java.net.Socket;

public class Connection implements Closeable {
    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;

    /**
     * Construct a connection.
     *
     * @param socket the socket.
     */
    public Connection(Socket socket) {
        this.socket = socket;
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Send a join request.
     *
     * @param username the username.
     */
    public void sendJoinRequest(String username) {
        synchronized (output) {
            try {
                output.writeUTF(username);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    /**
     * Receive a join request.
     *
     * @return the username.
     */
    public String receiveJoinRequest() {
        synchronized (input) {
            try {
                return input.readUTF();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    /**
     * Send a join response.
     *
     * @param res the result.
     */
    public void sendJoinResponse(boolean res) {
        synchronized (output) {
            try {
                output.writeBoolean(res);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    /**
     * Receive a join response.
     *
     * @return the result.
     */
    public boolean receiveJoinResponse() {
        synchronized (input) {
            try {
                return input.readBoolean();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    @Override
    public void close() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
