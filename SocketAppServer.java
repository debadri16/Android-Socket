import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Scanner;

class Main {

private static ServerSocket serverSocket;
private static Socket clientSocket;
private static InputStreamReader inputStreamReader;
private static BufferedReader bufferedReader;
private static String message;

public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    System.out.print("Enter port ");
    int port = sc.nextInt();

    try {
        serverSocket = new ServerSocket(port);  //Server socket

    } catch (IOException e) {
        System.out.println("Could not listen to specifeid port");
    }

    System.out.println("Server started. Listening to specified port");

    message = "";
    while(! message.equals("Over")){
        try {

            clientSocket = serverSocket.accept();   //accept the client connection
            System.out.println("Client accepted\n");
            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader); //get client msg  
        

            message = bufferedReader.readLine();
            System.out.println(message);
        
            
            inputStreamReader.close();
            clientSocket.close();

        } catch (IOException ex) {
            System.out.println("Problem in message reading");
        }
    }

    }
}  