import java.io.*;
import java.net.*;

public class Client {
    InputStreamReader inpt;
    PrintWriter out;
    BufferedReader bf;
    Socket client;
    public Client(){
        try {
            System.out.println("Sending request to server");
            client = new Socket("127.0.0.1",7777);
            System.out.println("Connection done");
            inpt = new InputStreamReader(client.getInputStream());
            bf = new BufferedReader(inpt);
            out = new PrintWriter(client.getOutputStream());
            startReading();
            startWriting();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void startReading(){
        System.out.println("Reading");
        Runnable r1 = ()->{
            while(true){
                try {
                    String message = bf.readLine();
                    if(message.equals("Exit")) {
                        System.out.println("Server stopped");
                        break;
                    }
                    System.out.println("Server: "+message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(r1).start();
    }
    public void startWriting(){
        Runnable r2 = ()->{
            System.out.println("Writing");
            while(!client.isClosed()){
                try{
                    BufferedReader temp = new BufferedReader(new InputStreamReader(System.in));
                    String content = temp.readLine();
                    out.println(content);
                    out.flush();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("Client is running: ");
        new Client();
    }
}
