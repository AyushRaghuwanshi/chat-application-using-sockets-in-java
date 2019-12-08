import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSIde2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        while (true) {
            ServerSocket serverSocket = new ServerSocket(5000);
            Socket socket = serverSocket.accept();
            System.out.println("connected");
            ReadData readData = new ReadData(socket);
            WriteData writeData = new WriteData(socket);
            writeData.start();
            readData.start();
            readData.join();
            writeData.join();
            socket.close();
            serverSocket.close();
            System.out.println("connection close successfully");
        }

    }

}

class ReadData extends Thread{

    private Socket socket;
    private DataInputStream inputStream;

    public ReadData(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){

        try {
            inputStream = new DataInputStream(socket.getInputStream());
            String string = "";
            while(!string.equals("bye")){
                string = inputStream.readUTF();
                System.out.println(string);
            }
            System.out.println("client wants to close the connection . write accept to close");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class WriteData extends Thread{

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Socket socket;

    public WriteData(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {

        inputStream = new DataInputStream(System.in);
        try {
            outputStream = new DataOutputStream(socket.getOutputStream());
            String string = "";
            while (!string.equals("accept")){
                string = inputStream.readLine();
                outputStream.writeUTF(string);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
