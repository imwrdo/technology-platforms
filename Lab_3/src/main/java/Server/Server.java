package Server;


import org.example.Operations;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(8081);
            LOGGER.info("Server is working.\nWaiting...");
            while(true){
                Socket clientSocket = serverSocket.accept();
                LOGGER.info("New client is connected: " + clientSocket);
                Thread thread = new Thread(new Operations(clientSocket));
                thread.start();
            }
        }catch(IOException e){
            LOGGER.severe("Error: " + e.getMessage());
        }

    }
}
