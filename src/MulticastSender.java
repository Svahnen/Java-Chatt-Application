import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MulticastSender {

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    InetAddress iadr;
    int port = 55555;
    String ip = "234.235.236.237";
    MulticastSocket socket;

    DatagramPacket packet;
    byte[] data;

    public MulticastSender() throws UnknownHostException, SocketException, IOException {
        this.iadr = InetAddress.getByName(ip);
        this.socket = new MulticastSocket(port);
    }

    public void sendMessage(String message) throws IOException {
        data = message.getBytes();
        packet = new DatagramPacket(data, data.length, iadr, port);
        socket.send(packet);
    }
}