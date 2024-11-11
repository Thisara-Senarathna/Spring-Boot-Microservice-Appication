
import java.net.*;
import java.io.*;

public class UDPClientWithRetransmission {
    public static void main(String[] args) throws Exception {
        DatagramSocket clientSocket = new DatagramSocket();
        clientSocket.setSoTimeout(3000);  // Set timeout to 3 seconds
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] sendData;
        byte[] receiveData = new byte[1024];

        String message = "Hello, Server!";
        sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8088);

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        int retries = 0;
        boolean receivedResponse = false;

        while (retries < 3 && !receivedResponse) {
            try {
                System.out.println("Sending message...");
                clientSocket.send(sendPacket);
                // Wait for the response
                clientSocket.receive(receivePacket);
                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Response from server: " + response);
                receivedResponse = true;
            } catch (SocketTimeoutException e) {
                retries++;
                System.out.println("Timeout, retrying... (" + retries + ")");
            }
        }
        if (!receivedResponse) {
            System.out.println("No response after 3 retries.");
        }
        clientSocket.close();
    }
}


import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServerWithDelay {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(8088);
        byte[] receiveData = new byte[1024];
        byte[] sendData;



        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received: " + message);

            // Simulate delay
            Thread.sleep(5000);

            String response = "Hello, Client!";
            sendData = response.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                    receivePacket.getAddress(), receivePacket.getPort());
            serverSocket.send(sendPacket);
        }
    }
}


