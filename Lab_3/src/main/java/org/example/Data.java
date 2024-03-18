package org.example;

import org.example.Message;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.io.IOException;


public class Data implements Runnable{
    private Socket clientSocket;
    public Data(Socket clientSocket){
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {
        try {
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        out.writeObject("Hello from server!");
        out.flush();
        int num = in.readInt();
        System.out.println("Recived from client number: " + num);

        out.writeObject("Ready for messages");
        out.flush();
        for (int i = 0; i < num; i++) {
            Message message = (Message) in.readObject();
            System.out.println("Recived message from client: "+message);
        }
        out.writeObject("Finished");
        out.flush();

        out.close();
        in.close();
        clientSocket.close();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
