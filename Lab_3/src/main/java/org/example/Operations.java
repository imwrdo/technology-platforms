package org.example;

import org.example.Message;
import Server.Server;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.io.IOException;
import java.util.logging.Logger;

public class Operations implements Runnable{
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private Socket clientSocket;
    public Operations(Socket clientSocket){
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            out.writeObject("Hello from server!");
            out.flush();
            out.writeObject("\nPlease, enter the number!");
            out.flush();

            int num = in.readInt();
            System.out.println("Received from client (port "+clientSocket.getPort() +") number: " + num);
            out.writeObject("Ready for messages.\n");
            out.flush();

            for (int i = 0; i < num; i++) {
                Message message = (Message) in.readObject();
                LOGGER.info("Received message from client (port "+clientSocket.getPort()+"): " + message.getContent());
            }
            out.writeObject("Finished");
            out.flush();

            out.close();
            in.close();
            clientSocket.close();
        }catch (IOException | ClassNotFoundException e) {
            LOGGER.severe("Error occurred: " + e.getMessage());
        }
    }
}
