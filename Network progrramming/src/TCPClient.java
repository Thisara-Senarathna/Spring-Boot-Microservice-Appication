import java.io.*;
import java.net.Socket;
public class TCPClient {
    public static void main(String[] args) throws IOException {
        // Connect to the server on localhost at port 8080
        Socket socket = new Socket("localhost", 8080);
        // Send a message to the server
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println("Hello, Server!");
        // Receive the server's response
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = in.readLine();
        System.out.println("Server says: " + response);
        // Close the socket connection
        socket.close();
    }
}
