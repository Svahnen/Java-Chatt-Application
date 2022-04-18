import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.time.LocalDateTime;

public class Multicast {
    int port = 55555;
    String ip = "234.235.236.237";
    String networkInterfaceName = "wlp3s0";
    InetAddress iadr = InetAddress.getByName(ip);
    InetSocketAddress group = new InetSocketAddress(iadr, port);
    NetworkInterface netIf = NetworkInterface.getByName(networkInterfaceName);
    MulticastSocket socket;

    public Multicast() throws IOException {
        this.socket = new MulticastSocket(port);
        this.socket.joinGroup(group, netIf);
    }

    public String getData() throws IOException {
        byte[] data = new byte[256];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        socket.receive(packet);
        System.out.println("Meddelande från " + packet.getAddress().getHostAddress() + " " + LocalDateTime.now());
        String message = new String(packet.getData(), 0, packet.getLength());
        return message;
    }

    public void sendMessage(String message) throws IOException {
        byte[] data = new byte[256];
        data = message.getBytes();
        DatagramPacket packet;
        packet = new DatagramPacket(data, data.length, iadr, port);
        socket.send(packet);
    }

    public void leave() {
        try {
            socket.leaveGroup(group, netIf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        socket.close();
    }

}
