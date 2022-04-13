import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.time.LocalDateTime;

public class MulticastReceiver {
    int port = 55555;
    String ip = "234.235.236.237";
    InetAddress iadr = InetAddress.getByName(ip);
    InetSocketAddress group = new InetSocketAddress(iadr, port);
    NetworkInterface netIf = NetworkInterface.getByName("wlp3s0");
    MulticastSocket multicast;
    byte[] data = new byte[256];

    public MulticastReceiver() throws IOException {
        this.multicast = new MulticastSocket(port);
        this.multicast.joinGroup(group, netIf);
    }

    public String getData() throws IOException {
        while (true) {
            DatagramPacket packet = new DatagramPacket(data, data.length);
            multicast.receive(packet);
            System.out.println("Meddelande från " + packet.getAddress().getHostAddress() + " " + LocalDateTime.now());
            String message = new String(packet.getData(), 0, packet.getLength());
            return message;
        }
    }

}
