package server;

import org.example.Message;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            LOGGER.info("Server started. Waiting for clients...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                LOGGER.info("Client connected: " + clientSocket);
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            LOGGER.severe("Error occurred: " + e.getMessage());
        }
    }
    static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        @Override
        public void run() {
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                outputStream.writeObject("ready");
                int n = (int) inputStream.readObject();
                outputStream.writeObject("ready for messages");
                for (int i = 0; i < n; i++) {
                    Message message = (Message) inputStream.readObject();
                    LOGGER.info("Received message from client: " + message.getContent());
                    outputStream.writeObject("Waiting for next message...");
                }
                outputStream.writeObject("finished");
                clientSocket.close();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.severe("Error occurred: " + e.getMessage());
            }
        }
    }
}