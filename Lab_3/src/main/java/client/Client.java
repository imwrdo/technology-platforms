package client;

import com.sun.security.jgss.GSSUtil;
import org.example.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;



public class Client {
    public static void main(String[] args){
        try {
            Socket clientSocket = new Socket("localhost",1234);
            Scanner scanner = new Scanner(System.in);
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            String napis = (String) in.readObject();
            System.out.println(napis);
            int num = Integer.parseInt(scanner.nextLine());
            out.writeInt(num);
            out.flush();
            String drugiNapis = (String) in.readObject();
            System.out.println(drugiNapis);
            for (int i = 0; i < num; i++) {
                System.out.println("Enter message " + (i + 1) + ":");
                String messageContent = scanner.nextLine();
                Message mTemp = new Message(i, messageContent);
                out.writeObject(mTemp);
                out.flush();
            }
            String trzeciNapis = (String) in.readObject();
            System.out.println(trzeciNapis);
            out.close();
            in.close();
            clientSocket.close();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
