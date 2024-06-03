import java.io.*;
import java.net.*;

public class Server {
    ServerSocket server;
    Socket client;
    BufferedReader bf;
    PrintWriter out;
    public Server(){
        try {
            System.out.println("Server is ready");
            server = new ServerSocket(7777);
            client = new Socket();
            client = server.accept();
            System.out.println("Connected");
            InputStreamReader in = new InputStreamReader(client.getInputStream());
            bf = new BufferedReader(in);
            bf.readLine();
            out = new PrintWriter(client.getOutputStream());
            startReading();
            startWriting();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  void  startReading(){
        Runnable r1 = ()->{
            System.out.println("Reading");
            while(true){
                try {
                    String message = bf.readLine();
                    if(message.equals("Exit")) {
                        System.out.println("Client is stopped!");
                        break;
                    }
                    System.out.println("Client: "+message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(r1).start();
    }
    public  void startWriting(){
        Runnable r2 = ()->{
            System.out.println("Writing");
            while (!client.isClosed()){
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String message = br.readLine();
                    out.println(message);
                    out.flush();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("Server is running....");
        new Server();
    }
}
