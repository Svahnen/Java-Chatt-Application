import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

public class DataReceiver {

    int port = 55555;
    byte[] data = new byte[256];

    DatagramSocket socket;

    public DataReceiver() throws Exception {
        this.socket = new DatagramSocket(port);
    }

    public String getData() throws UnknownHostException, SocketException, IOException {
        while (true) {
            DatagramPacket packet = new DatagramPacket(data, data.length);
            socket.receive(packet);
            System.out.println("Message from " + packet.getAddress().getHostAddress() + " " + LocalDateTime.now());
            String message = new String(packet.getData(), 0, packet.getLength());
            return message;
        }
    }
}
