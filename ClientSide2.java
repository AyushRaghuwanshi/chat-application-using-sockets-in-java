import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSide2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        String ip = "127.0.0.1";
        Socket socket = new Socket(ip,5000);
        System.out.println("connected");
        ReadDataClient readData = new ReadDataClient(socket);
        WriteDataClient writeData = new WriteDataClient(socket);
        readData.start();
        writeData.start();
        writeData.join();
        readData.join();
        socket.close();
        System.out.println("socket is closed by client side");
    }

}

class ReadDataClient extends Thread{

    private Socket socket;
    private DataInputStream inputStream;

    public ReadDataClient(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){

        try {
            inputStream = new DataInputStream(socket.getInputStream());
            String string = "";
            while(!string.equals("accept")){
                string = inputStream.readUTF();
                System.out.println(string);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class WriteDataClient extends Thread{

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Socket socket;

    public WriteDataClient(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {

        inputStream = new DataInputStream(System.in);
        try {
            outputStream = new DataOutputStream(socket.getOutputStream());
            String string = "";
            while (!string.equals("bye")){
                string = inputStream.readLine();
                outputStream.writeUTF(string);
            }
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
