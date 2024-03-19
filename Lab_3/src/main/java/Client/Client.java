package Client;

import org.example.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;



public class Client {
    public static void main(String[] args){
        try {
            Socket clientSocket = new Socket("127.0.0.1",8081);
            Scanner scanner = new Scanner(System.in);
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            String text = (String) in.readObject();
            System.out.println(text);
            String secondText = (String) in.readObject();
            System.out.println(secondText);
            int num = Integer.parseInt(scanner.nextLine());
            out.writeInt(num);
            out.flush();
            String thirdText = (String) in.readObject();
            System.out.println(thirdText);
            for (int i = 0; i < num; i++) {
                System.out.println("Enter message " + (i + 1) + ":");
                String messageContent = scanner.nextLine();
                Message mTemp = new Message(i, messageContent);
                out.writeObject(mTemp);
                out.flush();
            }
            String fourthText = (String) in.readObject();
            System.out.println(fourthText);
            out.close();
            in.close();
            clientSocket.close();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
